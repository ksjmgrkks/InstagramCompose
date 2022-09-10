package com.kks.instagramcompose

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.input.key.Key.Companion.U
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import com.kks.instagramcompose.data.Event
import com.kks.instagramcompose.data.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

const val USERS = "users"

@HiltViewModel
class InstagramViewModel @Inject constructor(
    val auth: FirebaseAuth,
    val db: FirebaseFirestore,
    val storage: FirebaseStorage
) : ViewModel() {

    val isLogin = mutableStateOf(false)
    val isProgress = mutableStateOf(false)
    val userData = mutableStateOf<UserData?>(null)
    val popupNotification = mutableStateOf<Event<String>?>(null)

    init {
//        auth.signOut()
        val currentUser = auth.currentUser
        isLogin.value = currentUser != null
        currentUser?.uid?.let {
            getUserData(it)
        }
    }

    fun onSignup(userName: String, email: String, password: String) {
        isProgress.value = true

        db.collection(USERS).whereEqualTo("userName", userName).get()
            .addOnSuccessListener { documents ->
                if (documents.size() > 0) {
                    handleException(customMessage = "해당 이름을 다른 유저가 사용하고 있어요")
                    isProgress.value = false
                } else {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                isLogin.value = true
                                createOrUpdateProfile(userName = userName)
                            } else {
                                handleException(task.exception, "회원가입을 실패하였습니다.")
                            }
                            isProgress.value = false
                        }
                }

            }
            .addOnFailureListener {

            }
    }

    private fun createOrUpdateProfile(
        name: String? = null,
        userName: String? = null,
        bio: String? = null,
        imageUrl: String? = null
    ){
        val uid = auth.currentUser?.uid
        val userData = UserData(
            userId = uid,
            name = name ?: userData.value?.name,
            userName = userName ?: userData.value?.userName,
            bio = bio ?: userData.value?.bio,
            imageUrl = imageUrl ?: userData.value?.imageUrl,
            following = userData.value?.following
        )

        uid?.let { uid ->
            isProgress.value = true
            db.collection(USERS).document(uid).get()
                .addOnSuccessListener {
                    if (it.exists()) {
                        it.reference.update(userData.toMap())
                            .addOnSuccessListener {
                                this.userData.value = userData
                                isProgress.value = false
                            }
                            .addOnFailureListener {
                                handleException(it, "유저를 업데이트 할 수 없습니다.")
                                isProgress.value = false
                            }
                    } else {
                        db.collection(USERS).document(uid).set(userData)
                        getUserData(uid)
                        isProgress.value = false
                    }
                }
                .addOnFailureListener { exc ->
                    handleException(exc, "유저를 생성할 수 없습니다.")
                    isProgress.value = false
                }
        }
    }

    private fun getUserData(uid: String) {
        isProgress.value = true
        db.collection(USERS).document(uid).get()
            .addOnSuccessListener {
                val user = it.toObject<UserData>()
                userData.value = user
                isProgress.value = false
            }
            .addOnFailureListener {
                handleException(it, "유저 데이터 검색을 실패하였습니다.")
                isProgress.value = false
            }
    }

    fun handleException(exception: Exception? = null, customMessage: String = "") {
        exception?.printStackTrace()
        val errorMessage = exception?.localizedMessage ?: ""
        val message = if (customMessage.isEmpty()) errorMessage else "$customMessage: $errorMessage"
        popupNotification.value = Event(message)
    }
}