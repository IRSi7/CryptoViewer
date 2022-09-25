package space.irsi7.cryptoviewer

import android.R
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import space.irsi7.cryptoviewer.databinding.ActivityMainBinding
import space.irsi7.cryptoviewer.ui.main.MainFragment
import space.irsi7.cryptoviewer.ui.main.MainViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        val toolbar: Toolbar = binding.toolbar

        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
//        setSupportActionBar(toolbar)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, MainFragment.newInstance())
                .commitNow()
        }
    }
}