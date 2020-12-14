package com.android.lidlapp.ui.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.lidlapp.R
import com.android.lidlapp.model.TicketModel
import com.android.lidlapp.ui.recycler.listener.TicketItemListener
import com.android.lidlapp.ui.recycler.viewholder.BaseViewHolder
import com.android.lidlapp.ui.recycler.viewholder.TicketViewHolder

class TicketsAdapter(private val ticketItemListener: TicketItemListener): RecyclerView.Adapter<BaseViewHolder<TicketModel>>() {

    private var tickets = mutableListOf<TicketModel>()

    override fun getItemCount() = tickets.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<TicketModel> {
        return TicketViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.ticket_item, parent, false),
            ticketItemListener
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<TicketModel>, position: Int) {
        holder.bindItem(tickets[position])
    }

    fun setTickets(newTickets: List<TicketModel>) {
        tickets.clear()
        tickets.addAll(newTickets)
        notifyDataSetChanged()
    }

    fun activateTicket(ticketId: String) {
        val ticketIndex = findIndexOfTicket(ticketId)
        setTicketActivation(ticketIndex, ACTIVATED)
    }

    fun deactivateTicket(ticketId: String) {
        val ticketIndex = findIndexOfTicket(ticketId)
        setTicketActivation(ticketIndex, DEACTIVATED)
    }

    private fun findIndexOfTicket(ticketId: String) =
        tickets.indexOfFirst { ticketId == it.id }

    private fun setTicketActivation(ticketIndex: Int, activation: Boolean) {
        if (ticketIndex != NOT_FOUND) {
            tickets[ticketIndex].activated = activation
            notifyItemChanged(ticketIndex, TicketViewHolder.getActivationChangedPayload())
        }
    }

    companion object {
        private const val NOT_FOUND = -1
        private const val ACTIVATED = true
        private const val DEACTIVATED = false
    }
}