package com.example.baat_cheet

import android.media.Image
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.baat_cheet.data.Event
import com.example.baat_cheet.data.USER_NODE
import com.example.baat_cheet.data.UserData
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class BCViewModel  @Inject constructor(

    val storage:FirebaseStorage,
    val auth: FirebaseAuth
): ViewModel()  {





    var db = Firebase.firestore
    var inProgress = mutableStateOf(false)
    val eventMustableState = mutableStateOf<Event<String>?>(null)
    var signIn = mutableStateOf(false)
    var userData = mutableStateOf<UserData?>(null)


    init {

        val currentUser = auth.currentUser
        signIn.value=currentUser!=null
        currentUser?.uid?.let {
            getUserData(it)
        }

    }


    fun signUp(name:String , number: String, email:String,password:String)
    {
        inProgress.value=true

        if(name.isEmpty() or number.isEmpty() or email.isEmpty() or password.isEmpty())
        {
            inProgress.value=false
            handleException(customMessage = "Please fill all fields")
            return
        }
        db.collection(USER_NODE).whereEqualTo("number",number).get().addOnSuccessListener {
            if(it.isEmpty)
            {
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                    @Composable
                    if(it.isSuccessful)
                    {
                        inProgress.value=false
                        signIn.value=true
                        createOrupdateProfile(name,number)
                    }
                    else
                    {
                        inProgress.value=false
                        handleException(it.exception,"SignUp Failed!!!")
                    }
                }

            }
            else{
                handleException(customMessage = "number Already exists")
                inProgress.value=false
            }
        }


        }
    fun handleException(exception:Exception? = null,customMessage : String="")
    {
        Log.e("Baat-cheet","exception : ",exception)
        exception?.printStackTrace()
        val errorMsg = exception?.localizedMessage?:""
        val message = if(customMessage.isNullOrEmpty()) errorMsg else customMessage

        eventMustableState.value= Event(message)
        inProgress.value=false

    }

    fun createOrupdateProfile(name: String?=null, number: String?=null,imageurl: String?=null)
    {

        var uid=auth.currentUser?.uid
        val userData =UserData(
            userId = uid,
            name=name?:userData.value?.name,
            number=number?:userData.value?.number,
            imageurl=imageurl?:userData.value?.imageurl,
        )

        uid?.let {
            inProgress.value=true
            db.collection(USER_NODE).document(uid).get().addOnSuccessListener {
                if (it.exists())
                {

                }
                else{
                    db.collection(USER_NODE).document(uid).set(userData)
                    inProgress.value=false
                    getUserData(uid)
                }
            }
                .addOnFailureListener {
                    handleException(it,"Can't Retrieve user")
                }
        }


    }

    private fun getUserData(uid: String) {
        inProgress.value=true
        db.collection(USER_NODE).document(uid).addSnapshotListener{
            value,error ->
            if(error!=null)
            {
                handleException(error,"cant retreive User")
            }

            if(value != null)
            {
                var user =value.toObject<UserData>()
                userData.value=user
                inProgress.value=false
            }
        }
    }

    fun Login(email:String,password:String)
    {
        inProgress.value=true

        if(email.isEmpty() or password.isEmpty())
        {
            inProgress.value=false
            handleException(customMessage = "Please fill all fields")
            return
        }
        else{
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                @Composable
                if(it.isSuccessful)
                {
                    inProgress.value=false
                    signIn.value=true
                    auth.currentUser?.uid?.let {
                        getUserData(it)
                    }
//                Toast.makeText(context,"Account Created Successfull",Toast.LENGTH_SHORT).show()
                }
                else
                {
                    inProgress.value=false
                    handleException(it.exception,"login Failed!!!")
                }
            }
        }


    }

    fun uploadProfileImage(uri: Uri)
    {
        uploadImage(uri)
        {
            createOrupdateProfile(imageurl = it.toString())
        }
    }

    private fun uploadImage(uri: Uri, onSuccess:(Uri)->Unit) {
        inProgress.value=true
        val storageref=storage.reference
        val uuid = UUID.randomUUID()
        val imageRef = storageref.child("image/$uuid")
        val uploadTask=imageRef.putFile(uri)
        uploadTask.addOnSuccessListener {
            val result=it.metadata?.reference?.downloadUrl
            result?.addOnSuccessListener(onSuccess)
            inProgress.value=false
        }
            .addOnFailureListener{
                handleException(it)
            }

    }
}

