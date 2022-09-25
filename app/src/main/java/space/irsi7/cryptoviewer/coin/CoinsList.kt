package space.irsi7.cryptoviewer.coin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import space.irsi7.cryptoviewer.R
import space.irsi7.cryptoviewer.model.Value
import space.irsi7.cryptoviewer.ui.main.MainViewModel
import java.io.File

//Класс хранящий картинки из одной директории
class CoinsList : Fragment() {
    private lateinit var fileList: RecyclerView
    lateinit var swipeContainer: SwipeRefreshLayout
    private lateinit var adapter: CoinAdapter
    private lateinit var glm: GridLayoutManager
    private lateinit var base: File
    private lateinit var path: String
    private val viewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        swipeContainer = inflater.inflate(R.layout.tokens_list, container, false) as SwipeRefreshLayout
        fileList = swipeContainer.findViewById(R.id.tokensList)
        glm = GridLayoutManager(context, 1)
        fileList.layoutManager = glm

        // Setup refresh listener which triggers new data loading

        swipeContainer.setOnRefreshListener {
            // Your code to refresh the list here.
            // Make sure you call swipeContainer.setRefreshing(false)
            // once the network request has completed successfully.
            viewModel.getTokensInfo()
            viewModel.isDownloading.observe(viewLifecycleOwner, Observer{
                if(!it){
                    swipeContainer.isRefreshing = false;
                }
            })
        }

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light);

        var first = false
        viewModel.chosenVal.observe(viewLifecycleOwner,Observer{
            if(first) {
                when (it) {
                    0 -> UpdateAdapter(viewModel.valueUSD.value!!)
                    1 -> UpdateAdapter(viewModel.valueEUR.value!!)
                }
            }
            first = true
        })
        setAdapter()
        return swipeContainer
    }

    private fun setAdapter() {
        val b = viewModel.tokenData.value
        adapter = CoinAdapter(context, viewModel.tokenData.value, viewModel.valueUSD.value)
        fileList.adapter = adapter
    }

    fun UpdateAdapter(values: List<Value>) {
        adapter.updateValuesSet(values)
    }

    companion object {
        fun newInstance() = CoinsList()
    }
}