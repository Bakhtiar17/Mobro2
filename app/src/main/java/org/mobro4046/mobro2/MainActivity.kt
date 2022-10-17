package org.mobro4046.mobro2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import org.mobro4046.mobro2.databinding.ActivityMainBinding
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.google.android.gms.maps.GoogleMap
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var myAdapter: MainAdapter
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myAdapter = MainAdapter()
        with(binding.recyclerView) {
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            setHasFixedSize(true)
            adapter = myAdapter
        }
        val formatter = SimpleDateFormat("dd MMM", Locale("ID", "id"))
        binding.chart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val pos = value.toInt() - 1
                val isValidPosition = pos >= 0 && pos < myAdapter.itemCount
                return if (isValidPosition)
                    formatter.format(myAdapter.getDate(pos)) else ""
            }
        }
        binding.chart.setOnChartValueSelectedListener(object :
            OnChartValueSelectedListener {
            override fun onValueSelected(entry: Entry?, highlight: Highlight) {
                val pos = myAdapter.itemCount - highlight.x.toInt()
                binding.recyclerView.scrollToPosition(pos)
            }

            override fun onNothingSelected() {}
        })


        with(binding.chart) {
            setNoDataText(getString(R.string.belum_ada_data))
            description.text = ""
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            axisLeft.axisMinimum = 0f
            axisRight.isEnabled = false
            legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            legend.setDrawInside(false)
        }


        viewModel.getData().observe(this, { myAdapter.setData(it) })
        viewModel.getStatus().observe(this, { updateProgress(it) })
        viewModel.getEntries().observe(this, { updateChart(it) })
    }

    private fun updateChart(entries: List<Entry>) {
        val dataset = LineDataSet(entries, getString(R.string.jumlah_kasus_positif))
        dataset.color = ContextCompat.getColor(this, R.color.purple_500)
        dataset.fillColor = dataset.color
        dataset.setDrawFilled(true)
        dataset.setDrawCircles(false)
        binding.chart.data = LineData(dataset)
        binding.chart.invalidate()
    }

    private fun updateProgress(status: ApiStatus) {
        when (status) {
            ApiStatus.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
            }
            ApiStatus.Success -> {
                binding.progressBar.visibility = View.GONE
            }
            ApiStatus.Failed -> {
                binding.progressBar.visibility = View.GONE
                binding.errorTextView.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_maps) {
            startActivity(Intent(this, MapsActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}