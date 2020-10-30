package com.aps.assignment.views

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.aps.assignment.R
import com.aps.assignment.databinding.FragmentGraphBinding
import com.aps.assignment.model.ResponseModel
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

/*
* Author:Ashish Savavshe
* */
class GraphFragment : BaseFragment() {

    private lateinit var binding: FragmentGraphBinding

    var statData :  List<ResponseModel.App>?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_graph,container,false)
        init()
        return binding.root
    }


    private fun init() {

        if (arguments != null) {
             statData   = arguments!!.getSerializable("selectedStore") as List<ResponseModel.App>?
        }

        if (binding.chart != null) {
            graphSetup()
        }

        drawLineChart()
    }



    private fun drawLineChart() {
        val lineEntries: List<Entry> = getDataSet()
        val lineDataSet = LineDataSet(lineEntries,"")
        //lineDataSet.axisDependency = YAxis.AxisDependency.LEFT
        lineDataSet.isHighlightEnabled = true
        lineDataSet.lineWidth = 2f
        lineDataSet.color = getHoloAccent()
        lineDataSet.setCircleColor(getHoloAccent())
        lineDataSet.circleRadius = 6f
        lineDataSet.circleHoleRadius = 3f
        lineDataSet.setDrawHighlightIndicators(true)
        lineDataSet.valueTextSize = 12f
        lineDataSet.valueTextColor = Color.WHITE
        lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        val lineData = LineData(lineDataSet)
        //chart.description.text = getString()
        binding.chart.description.textSize = 12f
        binding.chart.setDrawMarkers(true)
        binding.chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        binding.chart.animateY(1000)
        binding.chart.xAxis.isGranularityEnabled = true
        binding.chart.xAxis.typeface = Typeface.createFromAsset(activity!!.assets, "fonts/PrepSmartFont_EngCarnationDemo.otf")
        binding.chart.xAxis.textColor = Color.WHITE
        binding.chart.xAxis.setDrawAxisLine(false)
        binding.chart.xAxis.setDrawGridLines(true)
        binding.chart.xAxis.setCenterAxisLabels(false)
        binding.chart.xAxis.granularity = 1.0f
        binding.chart.xAxis.labelCount = lineDataSet.entryCount
        binding.chart.xAxis.xOffset = 12.toFloat()
        binding.chart.xAxis.labelRotationAngle = 45f
        binding.chart.description.isEnabled = false
        binding.chart.xAxis.axisMinimum = 1.0f
        binding.chart.xAxis.axisMaximum = 1.0f
        binding.chart.legend.isEnabled=false

        binding.chart.xAxis.valueFormatter = object : ValueFormatter() {
            private val mFormat = SimpleDateFormat("ddMMM", Locale.ENGLISH)
            override fun getFormattedValue(value: Float): String {
                val millis = TimeUnit.HOURS.toMillis(value.toLong())
                return mFormat.format(Date(value.toLong()))
            }
        }

        val yLeftAxis = binding.chart.axisLeft
        yLeftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        yLeftAxis.typeface = Typeface.createFromAsset(activity!!.assets, "fonts/PrepSmartFont_EngCarnationDemo.otf")
        yLeftAxis.textColor = Color.WHITE
        yLeftAxis.setDrawGridLines(false)

        yLeftAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return "$$value"
            }
        }

        var yRightAxis =binding.chart.axisRight
        yRightAxis.granularity = 10.toFloat()
        /* yRightAxis.axisMinimum = minMax.first.toFloat()
         yRightAxis.axisMaximum = minMax.second.toFloat()*/
        yRightAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        yRightAxis.typeface = Typeface.createFromAsset(activity!!.assets, "fonts/PrepSmartFont_EngCarnationDemo.otf")
        yRightAxis.textColor = Color.WHITE
        yRightAxis.setDrawGridLines(false)
        binding.chart.data = lineData
    }


    private fun getDataSet(): List<Entry> {
        val lineEntries: MutableList<Entry> = ArrayList()
        var i=0;
        statData!!.forEach {

           // var date = (SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH).parse(it.date).time).toFloat()
            lineEntries.add(
                Entry(it.data.total_sale.month_wise.toString().toFloat(),(it.data.total_sale.total).toFloat()
            )
            )
            i++
        }
        return lineEntries
    }



    private fun graphSetup() {
        // no description text
        binding.chart.description.isEnabled = false
        // enable touch gestures
        binding.chart.setTouchEnabled(true)
        binding.chart.dragDecelerationFrictionCoef = 0.9f
        // enable scaling and dragging
        binding.chart.isDragEnabled = true
        binding.chart.setScaleEnabled(false)
        binding.chart.setDrawGridBackground(false)
        binding.chart.isHighlightPerDragEnabled = false
//        chart.setViewPortOffsets(0f, 0f, 0f, 0f)

        // get the legend (only possible after setting data)
        val l = binding.chart.legend
        l.isEnabled = false

        val xAxis = binding.chart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
//        xAxis.typeface = tfLight
        xAxis.textSize = 10f
      //  xAxis.typeface = Typeface.createFromAsset(activity!!.assets, "font/montserrat_regular.ttf")
        xAxis.textColor = Color.WHITE
        xAxis.setDrawAxisLine(false)
        xAxis.setDrawGridLines(true)
        xAxis.setCenterAxisLabels(false)
//        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        /* val minMax = getMinMaxAmount()
         xAxis.axisMinimum = minMax.first.toFloat()
         xAxis.axisMaximum = minMax.second.toFloat()*/

        xAxis.axisMinimum = 1.0f
        xAxis.axisMaximum = 1.0f
        xAxis.granularity = 1f // one hour

        xAxis.valueFormatter = object : ValueFormatter() {
            private val mFormat = SimpleDateFormat("ddMMM", Locale.ENGLISH)
            override fun getFormattedValue(value: Float): String {
//                val millis = TimeUnit.HOURS.toMillis(value.toLong())
                return mFormat.format(Date(value.toLong()))
            }
        }

        val yAxis = binding.chart.axisLeft
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
      //  yAxis.typeface = Typeface.createFromAsset(activity!!.assets, "font/montserrat_regular.ttf")
        yAxis.textColor = Color.WHITE
        yAxis.setDrawGridLines(false)


        val minMax = getMinMaxAmount()
        yAxis.axisMinimum = minMax.first.toFloat()
        yAxis.axisMaximum = minMax.second.toFloat()


        val rightAxis = binding.chart.axisRight
        rightAxis.isEnabled = false
        setData(1000, 1000f)
    }

    private fun getMinMaxAmount(): Pair<Double, Double> {
        var min = statData!!.minBy { it.data.total_sale.total }?.data!!.total_sale.total.toDouble()
        var max = statData!!.maxBy { it.data.total_sale.total}?.data!!.total_sale.total.toDouble()
        return Pair(min ?: 0.toDouble(), max ?: 0.toDouble())
    }

    /* --------------- view initialiser ---------------- */
    private fun setData(count: Int, range: Float) {
        if (statData!!.isEmpty()) {
            binding.chart.clear()
            return
        }
        val values = ArrayList<Entry>()
        var i=1
        statData!!.forEach {
            values.add(
                Entry( (it.data.total_sale.total).toFloat(),i.toFloat()
            )
            )
            i++
        }


        // create a dataset and give it a type
        val set1 = LineDataSet(values, "DataSet 1")
        set1.color = getHoloAccent()
        set1.axisDependency = YAxis.AxisDependency.LEFT
        set1.valueTextColor = getHoloAccent()
        set1.lineWidth = 1.5f
        set1.setDrawCircles(false)
        set1.setDrawValues(false)
        set1.fillAlpha = 65
        set1.fillColor = getHoloAccent()
        set1.highLightColor = getHoloAccent()
        set1.mode = LineDataSet.Mode.CUBIC_BEZIER
        set1.setDrawCircleHole(false)

        // create a data object with the data sets
        /* var dataSets = ArrayList<ILineDataSet>()
         dataSets.add(set1)
         var lineData = LineData(dataSets)*/
        val data = LineData(listOf(set1))
        data.setValueTextColor(Color.WHITE)
        data.setValueTextSize(9f)

        // set data
        binding.chart.data = data
        binding.chart.invalidate()
        binding.chart.fitScreen()
    }

    private fun getHoloAccent() = Color.rgb(238, 69, 32)

    private fun getRandom(range: Float, start: Float): Float {
        return (Math.random() * range).toFloat() + start
    }



    companion object {
        @JvmStatic
        fun newInstance() =
            GraphFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
