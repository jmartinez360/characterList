package com.android.lidlapp.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.android.lidlapp.R
import com.android.lidlapp.databinding.ActivationButtonBinding

class ActivationButton(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private val binding: ActivationButtonBinding =
        ActivationButtonBinding.inflate(LayoutInflater.from(context), this, true)

    private var isTicketActivated = false

    init {
        background = ContextCompat.getDrawable(context, R.drawable.activation_button_selector)
    }

    fun setActivated() {
        setActivationState(ACTIVATED)
        setToNotLoadingState()
        binding.activateButtonText.text = context.getText(R.string.deactivate_ticket)
    }

    fun setDeactivated() {
        setActivationState(DEACTIVATED)
        setToNotLoadingState()
        binding.activateButtonText.text = context.getText(R.string.activate_ticket)
    }

    private fun setActivationState(activationState: Boolean) {
        if (isTicketActivated != activationState) {
            isTicketActivated = activationState
            refreshDrawableState()
            setupTextColor()
        }
    }

    private fun setupTextColor() {
        binding.activateButtonText.setTextColor(context.getColor(getColorRes()))
    }

    private fun getColorRes(): Int {
        return if (isTicketActivated) {
            R.color.activationButton
        } else {
            R.color.white
        }
    }

    fun setLoading() {
        setToLoadingState()
    }

    private fun setToNotLoadingState() {
        isEnabled = true
        binding.activationProgress.visibility = View.GONE
        binding.activateButtonText.visibility = View.VISIBLE
    }

    private fun setToLoadingState() {
        isEnabled = false
        binding.activationProgress.visibility = View.VISIBLE
        binding.activateButtonText.visibility = View.INVISIBLE
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        return if (isTicketActivated) {
            val newDrawableState = super.onCreateDrawableState(extraSpace + 1)
            mergeDrawableStates(newDrawableState, ACTIVATION_STATE)
            newDrawableState
        } else {
            super.onCreateDrawableState(extraSpace)
        }
    }

    companion object {
        private val ACTIVATION_STATE = intArrayOf(R.attr.ticketActivated)

        private const val ACTIVATED = true
        private const val DEACTIVATED = false
    }
}