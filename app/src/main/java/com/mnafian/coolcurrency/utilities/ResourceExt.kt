package com.mnafian.coolcurrency.utilities

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.*
import android.util.TypedValue
import android.widget.EditText
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputEditText
import prod.divrt.runner.utilities.CoreConfig
import java.text.NumberFormat
import java.util.*


/**
 * Created on : December/10/2018
 * Author     : mnafian
 */

fun Int.resString(): String = CoreConfig.context.resources.getString(this)

fun Int.resString(vararg formatArg: Any): String = CoreConfig.context.resources.getString(this, *formatArg)

inline val Float.dp: Int
    get() {
        val context = CoreConfig.context
        val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, context.resources.displayMetrics)

        return Math.round(px)
    }

inline val Int.dp: Int get() = this.toFloat().dp

inline val Float.sp: Int
    get() = Math.round(
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP, this,
            CoreConfig.context.resources.displayMetrics
        )
    )

inline val Int.sp: Int
    get() = toFloat().sp


fun Int.resColor(): Int = ContextCompat.getColor(CoreConfig.context, this)

fun Int.resDimen(): Int = CoreConfig.context.resources.getDimensionPixelSize(this)

fun Int.resDimensionPixelOffset(): Int = CoreConfig.context.resources.getDimensionPixelOffset(this)

fun Int.attrColor(context: Context?): Int {
    context?.let {
        val typedValue = TypedValue()
        val theme = context.theme
        theme.resolveAttribute(this, typedValue, true)
        return typedValue.data
    }
    return Color.BLACK
}

fun Int.resStringArray(): Array<out String>? = CoreConfig.context.resources.getStringArray(this)

fun String.toSpanned(): Spanned {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION")
        return Html.fromHtml(this)
    }
}

fun Int.resDrawable(): Drawable? = ActivityCompat.getDrawable(CoreConfig.context, this)

fun TextInputEditText.addFilter(filter: InputFilter) {
    filters =
            if (filters.isEmpty()) {
                arrayOf(filter)
            } else {
                filters
                    .toMutableList()
                    .apply {
                        removeAll { it.javaClass == filter.javaClass }
                        add(filter)
                    }
                    .toTypedArray()
            }
}

fun EditText.addCurrencyFormatter() {

    // Reference: https://stackoverflow.com/questions/5107901/better-way-to-format-currency-input-edittext/29993290#29993290
    this.addTextChangedListener(object : TextWatcher {

        private var current = ""

        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            if (s.toString() != current) {
                this@addCurrencyFormatter.removeTextChangedListener(this)
                // strip off the currency symbol

                // Reference for this replace regex: https://stackoverflow.com/questions/5107901/better-way-to-format-currency-input-edittext/28005836#28005836
                val cleanString = s.toString().replace("\\D".toRegex(), "")
                val parsed = if (cleanString.isBlank()) 0.0 else cleanString.toDouble()
                // format the double into a currency format
                val locale = Locale("in", "ID")
                val formated = NumberFormat.getCurrencyInstance(locale)
                    .format(parsed)

                current = formated
                this@addCurrencyFormatter.setText(formated)
                this@addCurrencyFormatter.setSelection(formated.length)

                this@addCurrencyFormatter.addTextChangedListener(this)
            }
        }
    })

}
