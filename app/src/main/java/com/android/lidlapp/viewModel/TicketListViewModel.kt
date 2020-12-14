package com.android.lidlapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.android.domain.model.TicketDomainModel
import com.android.domain.usecase.ActivateTicket
import com.android.domain.usecase.DeactivateTicket
import com.android.domain.usecase.GetTickets
import com.android.lidlapp.mapper.DomainToModelMapper
import com.android.lidlapp.model.TicketModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class TicketListViewModel : BaseViewModel() {

    abstract fun getTicketList()
    abstract fun activateDeactivateTicket(ticketModel: TicketModel)

    abstract val ticketListLiveData: LiveData<List<TicketModel>>
    abstract val activatedTicketsCountLiveData: LiveData<Int>
    abstract val activatedTicketLiveData: LiveData<String>
    abstract val deactivatedTicketLiveData: LiveData<String>
}

class TicketListViewModelImpl(
    private val getTickets: GetTickets,
    private val activateTicket: ActivateTicket,
    private val deactivateTicket: DeactivateTicket,
    private val mapper: DomainToModelMapper
) : TicketListViewModel() {

    private val _ticketListLiveData = MediatorLiveData<List<TicketModel>>()
    override val ticketListLiveData: LiveData<List<TicketModel>>
        get() = _ticketListLiveData

    private val _activatedTicketLiveData = MediatorLiveData<String>()
    override val activatedTicketLiveData: LiveData<String>
        get() = _activatedTicketLiveData

    private val _deactivatedTicketLiveData = MediatorLiveData<String>()
    override val deactivatedTicketLiveData: LiveData<String>
        get() = _deactivatedTicketLiveData

    private val _activatedTicketsCountLiveData = MediatorLiveData<Int>()
    override val activatedTicketsCountLiveData: LiveData<Int>
        get() = _activatedTicketsCountLiveData

    private val _loadingLiveData = MediatorLiveData<Boolean>()
    override val loadingLiveData: LiveData<Boolean>
        get() = _loadingLiveData

    private val _errorLiveData = MediatorLiveData<Boolean>()
    override val errorLiveData: LiveData<Boolean>
        get() = _errorLiveData

    private var ticketList: List<TicketModel> = mutableListOf()
    private var activatedTicketsCount: Int = ACTIVATED_TICKETS_COUNT_DEF

    override fun getTicketList() {
        viewModelScope.launch { getTicketListSetup() }
    }

    private suspend fun getTicketListSetup() {
        showLoading()
        getTickets.execute()
            .catch { onError() }
            .collect { onResult(it) }
    }

    private fun showLoading() {
        _loadingLiveData.postValue(SHOW)
    }

    private fun onError() {
        hideLoading()
        showError()
    }

    private fun showError() {
        _errorLiveData.postValue(SHOW)
    }

    private fun onResult(domainList: List<TicketDomainModel>) {
        hideLoading()
        mapDomainTicketsToModelTickets(domainList)
        showTicketList()
        countActivatedTickets()
        showActivatedTickets()
    }

    private fun hideLoading() {
        _loadingLiveData.postValue(HIDE)
    }

    private fun mapDomainTicketsToModelTickets(domainList: List<TicketDomainModel>) {
        ticketList = mapper.mapToTicketModelList(domainList)
    }

    private fun showTicketList() {
        _ticketListLiveData.postValue(ticketList)
    }

    private fun countActivatedTickets() {
        activatedTicketsCount = ticketList.filter { it.activated }.size
    }

    private fun showActivatedTickets() {
        _activatedTicketsCountLiveData.postValue(activatedTicketsCount)
    }

    override fun activateDeactivateTicket(ticketModel: TicketModel) {
        val ticketId = ticketModel.id
        if (shouldActivateTicket(ticketModel)) {
            activateTicket(ticketId)
        } else {
            deactivateTicket(ticketId)
        }
    }

    private fun shouldActivateTicket(ticketModel: TicketModel) =
        !ticketModel.activated

    private fun activateTicket(ticketId: String) {
        viewModelScope.launch { sendTicketActivation(ticketId) }
    }

    private suspend fun sendTicketActivation(ticketId: String) {
        activateTicket.execute(ticketId)
            .catch { /* show error */ }
            .collectLatest {
                informTicketHasBeenActivated(it)
            }
    }

    private fun informTicketHasBeenActivated(ticketId: String) {
        _activatedTicketLiveData.postValue(ticketId)
        incrementAndInformActivatedTicketsCount()
    }

    private fun deactivateTicket(ticketId: String) {
        viewModelScope.launch { sendTicketDeactivation(ticketId) }
    }

    private suspend fun sendTicketDeactivation(ticketId: String) {
        deactivateTicket.execute(ticketId)
            .catch { /* show error */ }
            .collectLatest { informTicketHasBeenDeactivated(it) }
    }

    private fun informTicketHasBeenDeactivated(ticketId: String) {
        _deactivatedTicketLiveData.postValue(ticketId)
        decrementAndInformActivatedTicketsCount()
    }

    private fun incrementAndInformActivatedTicketsCount() {
        activatedTicketsCount++
        showActivatedTickets()
    }

    private fun decrementAndInformActivatedTicketsCount() {
        activatedTicketsCount--
        showActivatedTickets()
    }

    companion object {
        private const val ACTIVATED_TICKETS_COUNT_DEF = 0
    }
}