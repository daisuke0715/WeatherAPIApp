package com.nemo.sampleweatherapp.view.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nemo.sampleweatherapp.databinding.FragmentWeatherMainBinding
import com.nemo.sampleweatherapp.databinding.ZWeatherListDateBinding
import com.nemo.sampleweatherapp.databinding.ZWeatherListWeatherMainDataBinding
import com.nemo.sampleweatherapp.viewModel.weather.WeatherMainViewModel.WeatherMainData
import com.nemo.sampleweatherapp.viewModel.weather.WeatherMainViewModel.WeatherDate
import com.nemo.sampleweatherapp.viewModel.weather.WeatherMainViewModel.WeatherItemModel
import java.lang.IllegalArgumentException

class WeatherMainFragment: Fragment() {
    private var _binding: FragmentWeatherMainBinding? = null
    private val binding: FragmentWeatherMainBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

class WeatherListAdapter : ListAdapter<WeatherItemModel, WeatherListViewHolder>(diffUtilCallback) {
    override fun getItemViewType(position: Int): Int {
        return when(currentList[position]) {
            is WeatherDate -> ViewType.DATE.ordinal
            is WeatherMainData -> ViewType.MAIN.ordinal
            else -> throw IllegalArgumentException("想定外の型です")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherListViewHolder {
        return when(viewType) {
            ViewType.DATE.ordinal -> WeatherDateViewHolder.create(parent)
            ViewType.MAIN.ordinal -> WeatherMainDataViewHolder.create(parent)
            else -> throw IllegalArgumentException("想定外のViewTypeです")
        }
    }

    override fun onBindViewHolder(holder: WeatherListViewHolder, position: Int) {

    }

    private enum class ViewType {
        DATE, MAIN
    }
}

private val diffUtilCallback = object : DiffUtil.ItemCallback<WeatherItemModel>() {
    override fun areItemsTheSame(oldItem: WeatherItemModel, newItem: WeatherItemModel): Boolean {
        return oldItem.isSameAs(newItem)
    }

    override fun areContentsTheSame(oldItem: WeatherItemModel, newItem: WeatherItemModel): Boolean {
        return oldItem.hasSameContent(newItem)
    }
}


abstract class WeatherListViewHolder(view: View) : RecyclerView.ViewHolder(view)

class WeatherDateViewHolder(
    binding: ZWeatherListDateBinding
) : WeatherListViewHolder(binding.root) {
    companion object {
        fun create(parent: ViewGroup): WeatherDateViewHolder {
            val binding = ZWeatherListDateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return WeatherDateViewHolder(binding)
        }
    }
}

class WeatherMainDataViewHolder(
    binding: ZWeatherListWeatherMainDataBinding
) : WeatherListViewHolder(binding.root) {
    companion object {
        fun create(parent: ViewGroup): WeatherMainDataViewHolder {
            val binding = ZWeatherListWeatherMainDataBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return WeatherMainDataViewHolder(binding)
        }
    }
}
