package edu.temple.basicbrowser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import java.net.URL

class MainActivity : AppCompatActivity() {

    lateinit var urlEditText: EditText
    lateinit var goButton: ImageButton
    lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        urlEditText = findViewById(R.id.urlEditText)
        goButton = findViewById(R.id.goButton)
        webView = findViewById(R.id.webView)

        // Allow your browser to intercept hyperlink clicks
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
            }
        }

        goButton.setOnClickListener {
            val enteredUrl = urlEditText.text.toString()
            val validUrl = validateAndFixUrl(enteredUrl)

            if (validUrl != null) {
                webView.loadUrl(validUrl)
            } else {
                // Display an error message if the URL is not valid
                Toast.makeText(this, "Invalid URL", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateAndFixUrl(url: String): String? {
        try {
            val parsedUrl = URL(url)
            // If the URL doesn't have a scheme (e.g., http:// or https://), add "https://" by default
            if (parsedUrl.protocol == null) {
                return "https://$url"
            }
            return url
        } catch (e: Exception) {
            return null  // URL is not valid
        }
    }
}
