package com.publicmethod.dewildte

import design.DewildteTheme
import presentation.MobileApp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContent {
			DewildteTheme {
				MobileApp()
			}
		}
	}
}

@Preview
@Composable
fun AppAndroidPreview() {
	DewildteTheme {
		MobileApp()
	}
}