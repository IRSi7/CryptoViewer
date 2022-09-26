package space.irsi7.cryptoviewer

import android.R
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import space.irsi7.cryptoviewer.databinding.ActivityMainBinding
import space.irsi7.cryptoviewer.ui.main.CoinInfoFragment
import space.irsi7.cryptoviewer.ui.main.MainFragment
import space.irsi7.cryptoviewer.ui.main.MainViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var listFragment: MainFragment
    private lateinit var infoFragment: CoinInfoFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        listFragment = MainFragment.newInstance()
        infoFragment = CoinInfoFragment.newInstance()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        if (savedInstanceState == null) {

            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, listFragment)
                .commitNow()
        }
        var firstcall = false
        viewModel.isSelected.observe(this) {
            if(!firstcall){
                if(it){
                    supportFragmentManager.beginTransaction()
                        .replace(binding.container.id, infoFragment)
                        .commitNow()
                } else {
                    supportFragmentManager.beginTransaction()
                        .replace(binding.container.id, listFragment)
                        .commitNow()
                }
            }
        }
    }
}