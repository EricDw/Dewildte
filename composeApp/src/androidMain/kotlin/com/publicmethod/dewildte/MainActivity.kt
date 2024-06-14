package com.publicmethod.dewildte

import design.DewildteTheme
import presentation.MobileApp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

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