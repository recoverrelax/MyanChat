package com.lolchat.myanmarking.myanchat.ui.adapter

import android.support.v7.widget.RecyclerView
import android.transition.TransitionManager
import android.view.ViewGroup
import com.lolchat.myanmarking.myanchat.io.interfaces.IRecycleableView
import com.lolchat.myanmarking.myanchat.io.model.item_view_interfaces.IFriendEntity
import com.lolchat.myanmarking.myanchat.io.model.xmpp.FriendEntity
import com.lolchat.myanmarking.myanchat.ui.fragment.FragFriendList.IFriendListManager
import com.lolchat.myanmarking.myanchat.ui.view_item.FriendView
import org.jetbrains.anko.onClick
import org.jetbrains.anko.onLongClick

class FragFriendListAdapter(
        val longClickListener: OnFriendLongClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), IFriendListManager {

    private var friendList: List<FriendEntity> = emptyList()
    private val INFO_LOG = true
    private var recyclerView: RecyclerView? = null

    private var expandedItemTracker: MutableList<String> = mutableListOf()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        holder as FriendListViewHolder
        holder.setItem()
    }

    override fun getItemCount(): Int {
        return friendList.size
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView?) {
        super.onDetachedFromRecyclerView(recyclerView)
        this.recyclerView = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FriendListViewHolder(FriendView(parent.context, null, 0), longClickListener)
    }

    override fun onFreshData(friendEntityList: List<FriendEntity>) {
        this.friendList = friendEntityList
        notifyDataSetChanged()
    }

    override fun onDataChanged(friendEntityList: List<FriendEntity>) {
        this.friendList = friendEntityList
        notifyDataSetChanged()
    }

    inner class FriendListViewHolder(itemView: FriendView, longClickListener: OnFriendLongClickListener) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.onLongClick {
                val item = getFriend(adapterPosition)
                if (item.isFriendOnline) {
                    longClickListener.onFriendLongClick(item.name, adapterPosition)
                    true
                } else {
                    false
                }
            }
        }

        fun setItem() {
            val item = getFriend(adapterPosition)
            itemView as IFriendEntity
            itemView.setItem(item)

            val collapseId = item.name

            if (canExpand(friend = item) && isExpanded(itemName = collapseId)) {
                itemView.expand()
            }

            itemView.onClick {
                if (isExpanded(collapseId)) {
                    TransitionManager.beginDelayedTransition(recyclerView)
                    itemView.collapse()
                    expandedItemTracker.remove(collapseId)
                } else if (canExpand(friend = item)) {
                    TransitionManager.beginDelayedTransition(recyclerView)
                    expandedItemTracker.add(collapseId)
                    itemView.expand()
                }
            }
        }

        private fun getFriend(position: Int): FriendEntity
            = friendList[position]
    }

    private fun isExpanded(itemName: String): Boolean {
        return expandedItemTracker.contains(itemName)
    }

    private fun canExpand(friend: FriendEntity): Boolean {
        return friend.isOnlineInChampSelectOrPlaying()
    }


    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        (holder.itemView as? IRecycleableView)?.onRecycle()
    }
}

interface OnFriendLongClickListener {
    fun onFriendLongClick(friendName: String, position: Int)
}