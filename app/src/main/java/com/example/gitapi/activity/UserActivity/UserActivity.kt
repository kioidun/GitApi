package com.example.gitapi.activity.UserActivity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.gitapi.rest.GitHubUserEndPoints
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.gitapi.activity.Repositories.Repositiories
import com.example.gitapi.model.AcessToken
import com.example.gitapi.rest.GitApiClient

class UserActivity : AppCompatActivity() {
    private lateinit var mavatarImg: ImageView
    private lateinit var muserName: TextView
    private lateinit var mfollowers: TextView
    private lateinit var mfollowing: TextView
    private lateinit var mlogin: TextView
    private lateinit var memail: TextView
    private lateinit var mownedrepos: Button
    private lateinit var extras: Bundle
    private lateinit var newString: String
    private lateinit var code: String
    var myImage: Bitmap? = null
    var deliveryList: MutableList<GitHubUser>? = null
    var clientID: String = "ea39d14d748eadf55859"
    var clientSecret: String = "565dfcdaa83070204a41612b02cc59d17baabbc3"
    var redirectUri: String = "https://kioigitapi.herokuapp.com/callback"

    private lateinit var mUserActivityViewModel: UserActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.gitapi.R.layout.activity_user)

        mavatarImg = findViewById(com.example.gitapi.R.id.imageview)
        muserName = findViewById(com.example.gitapi.R.id.username)
        mfollowers = findViewById(com.example.gitapi.R.id.followers)
        mfollowing = findViewById(com.example.gitapi.R.id.following)
        mlogin = findViewById(com.example.gitapi.R.id.login)
        memail = findViewById(com.example.gitapi.R.id.email)
        mownedrepos = findViewById(com.example.gitapi.R.id.ownedReposBtn)

        extras = intent.extras!!
        newString = extras.getString("STRING_I_NEED").toString()

        extras = intent.extras!!
        code = extras.getString("Code").toString()
        mownedrepos.setOnClickListener {
            loadOwnRepos()
        }
        mUserActivityViewModel =
            ViewModelProviders.of(this, GitHubUserFactory(application, newString, clientID, clientSecret, code))
                .get(UserActivityViewModel::class.java)
        mUserActivityViewModel.init()
        mUserActivityViewModel.allDeliveryItems.observe(this, Observer { userList ->
            deliveryList?.addAll(userList)
            deliveryList?.get(0)?.login

            deliveryList?.get(1)
            deliveryList?.get(2)
            if (userList == null) {
                muserName.text = " No name provided"
            } else {
                muserName.text = "Username:" + userList[0].name
            }
            mlogin.text = "login:" + userList[0].login
            mfollowers.text = "Followers:" + userList[0].followers
            mfollowing.text = "Following:" + userList[0].following

            if (userList == null) {
                memail.text = "No email provided"
            } else {
                memail.text = "email:" + userList[0].email
            }

            var task: ImageDownloader = ImageDownloader()

            try {
                myImage = task.execute(userList[0].email).get()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            mavatarImg.setImageBitmap(myImage)
            mavatarImg.layoutParams.height = 220
            mavatarImg.layoutParams.width = 220
        })
    }
    override fun onResume() {
        super.onResume()
        var uri = intent.data

        if (code != null) {
            Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show()

            val apiService = GitApiClient.client!!.create(GitHubUserEndPoints::class.java)

            val call = apiService.getAccessToken(clientID, clientSecret, code)

            call.enqueue(object : Callback<AcessToken> {
                override fun onFailure(call: Call<AcessToken>, t: Throwable) {
                    Toast.makeText(this@UserActivity, "no", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<AcessToken>, response: Response<AcessToken>) {
                    Toast.makeText(this@UserActivity, "yes", Toast.LENGTH_SHORT).show()
                }
            })

        }
    }

    fun loadOwnRepos() {
        var i = Intent(this@UserActivity, Repositiories::class.java)
        i.putExtra("username", newString)
        startActivity(i)
    }
    inner class ImageDownloader : AsyncTask<String, Void, Bitmap>() {
        override fun doInBackground(vararg urls: String): Bitmap? {

            try {

                val url = URL(urls[0])
                val connection = url.openConnection() as HttpURLConnection
                connection.connect()
                val inputStream = connection.inputStream
                return BitmapFactory.decodeStream(inputStream)

            } catch (e: MalformedURLException) {

                e.printStackTrace()

            } catch (e: IOException) {

                e.printStackTrace()

            }

            return null
        }
    }
}

