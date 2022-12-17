package com.thewind.space.main.ui.music.searchpage.ui.searchbar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.GradientDrawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import com.thewind.space.R
import com.thewind.space.databinding.SearchBarLayoutBinding
import com.thewind.spacecore.extension.dp
import com.thewind.spacecore.uiutil.ViewUtils.dpToPx

/**
 * @author: read
 * @date: 2022/12/10 上午2:23
 * @description:
 */
class CommonSearchBarView @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attr, defStyleAttr, defStyleRes) {

    var searchListener: SearchBarViewListener? = null
    var mShadowColor: Int = Color.BLACK
    var mShadowSize: Int = 4.dp().toInt()
    private var mShadowPainter: Paint = Paint().apply {
        style = Paint.Style.FILL
        color = mShadowColor
    }

    private var binding: SearchBarLayoutBinding

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.search_bar_layout, null, false)
        addView(view)
        binding = SearchBarLayoutBinding.bind(view)
        binding.tvSearch.setOnClickListener {
            searchListener?.onSearchClick(binding.etInput.text.toString())
        }
        binding.ivBack.setOnClickListener {
            searchListener?.onBackClick()
        }

        binding.etInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                searchListener?.onTextChanged(s.toString())
            }
        })
        binding.etInput.setOnEditorActionListener { v, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    hideKeyboard()
                    searchListener?.onSearchClick(v?.text.toString())
                }
            }
            true
        }
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        (0..mShadowSize).forEach {
            mShadowPainter.alpha = it * 16 / mShadowSize
            canvas?.drawLine(0f, (height - it).toFloat(), width.toFloat(), (height - it).toFloat(), mShadowPainter)
        }
    }

    fun applyColorTheme(bgColor: Int, eleColor: Int) {
        binding.root.background = GradientDrawable().apply {
            setColor(bgColor)
        }
        binding.etInput.setTextColor(eleColor)
        binding.etInput.setHintTextColor(eleColor)
        binding.tvSearch.setTextColor(eleColor)
        binding.ivBack.drawable.apply {
            setTint(eleColor)
        }
        val hintBgColor = if (bgColor == Color.WHITE) 0x11000000 else 0x33FFFFFF
        binding.clSearchTextContainer.background = GradientDrawable().apply {
            setColor(hintBgColor)
            cornerRadius = dpToPx(1000).toFloat()
        }
    }

    fun applyHintText(text: String?) {
        binding.etInput.hint = text?:""
    }

    /**
     * 隐藏软键盘
     * @param context :上下文
     * @param view    :一般为EditText
     */
    fun hideKeyboard() {
        val manager: InputMethodManager = context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(windowToken, 0)
    }


}


interface SearchBarViewListener {
    fun onSearchClick(text: String)
    fun onInputClick()
    fun onBackClick()
    fun onTextChanged(text: String)
}