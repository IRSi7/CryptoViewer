package space.irsi7.cryptoviewer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import space.irsi7.cryptoviewer.databinding.ActivityMainBinding
import space.irsi7.cryptoviewer.ui.main.TokenInfoFragment.InformFragment
import space.irsi7.cryptoviewer.ui.main.MainFragment
import space.irsi7.cryptoviewer.ui.main.MainViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var listFragment: MainFragment
    private lateinit var infoFragment: InformFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        listFragment = MainFragment.newInstance()
        infoFragment = InformFragment.newInstance()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        if (savedInstanceState == null) {

            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, listFragment)
                .setReorderingAllowed(true)
                .commitNow()
        }

        viewModel.selected.observe(this) {
            if(it != null){
                    supportFragmentManager.beginTransaction()
                        .replace(binding.container.id, infoFragment)
                        .commitNow()
                } else {
                    supportFragmentManager.beginTransaction()
                        .replace(binding.container.id, listFragment)
                        .setReorderingAllowed(true)
                        .commitNow()
                }
            }
    }
}