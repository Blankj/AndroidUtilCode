package com.blankj.utildebug.base.rv;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/08/22
 *     desc  :
 * </pre>
 */
public class BaseItemAdapter<Item extends BaseItem> extends RecyclerView.Adapter<ItemViewHolder> {

    public  List<Item>   mItems;
    private RecyclerView mRecyclerView;

    public BaseItemAdapter() {
        this(false);
    }

    public BaseItemAdapter(boolean hasStableIds) {
        setHasStableIds(hasStableIds);
    }

    @Override
    public final int getItemViewType(int position) {
        Item item = mItems.get(position);
        item.mAdapter = this;
        return item.getViewType();
    }

    @Override
    public long getItemId(int position) {
        return mItems.get(position).getItemId();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return Item.onCreateViewHolder(parent, viewType);
    }

    @Override
    public final void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        mItems.get(position).bind(holder, position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public void onViewRecycled(@NonNull ItemViewHolder holder) {
        super.onViewRecycled(holder);
        int position = holder.getAdapterPosition();
        if (position < 0 || position >= mItems.size()) {
            return;
        }
        mItems.get(position).onViewRecycled(holder, position);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void setItems(@NonNull final List<Item> items) {
        mItems = items;
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(mItems);
    }

    public Item getItem(@IntRange(from = 0) final int position) {
        return mItems.get(position);
    }

    public boolean isEmpty() {
        return mItems.isEmpty();
    }

    ///////////////////////////////////////////////////////////////////////////
    // id
    ///////////////////////////////////////////////////////////////////////////

    public Item getItemById(final long id) {
        int itemIndex = getItemIndexById(id);
        if (itemIndex != -1) {
            return mItems.get(itemIndex);
        } else {
            return null;
        }
    }

    public int getItemIndexById(final long id) {
        for (int i = 0; i < mItems.size(); i++) {
            if (getItemId(i) == id) {
                return i;
            }
        }
        return -1;
    }

    public boolean hasItemWithId(final long id) {
        return getItemIndexById(id) != -1;
    }

    public int replaceItemById(final long id, @NonNull final Item item) {
        return replaceItemById(id, item, false);
    }

    public int replaceItemById(final long id, @NonNull final Item item, boolean notifyChanged) {
        int itemIndex = getItemIndexById(id);
        if (itemIndex != -1) {
            replaceItem(itemIndex, item, notifyChanged);
        }
        return itemIndex;
    }

    public int removeItemById(final long id) {
        return removeItemById(id, false);
    }

    public int removeItemById(final long id, boolean notifyRemoved) {
        for (int i = 0; i < mItems.size(); i++) {
            if (getItemId(i) == id) {
                removeItem(i, notifyRemoved);
                return i;
            }
        }
        return -1;
    }

    ///////////////////////////////////////////////////////////////////////////
    // operate
    ///////////////////////////////////////////////////////////////////////////

    public void updateItem(@NonNull final Item item) {
        int itemIndex = mItems.indexOf(item);
        if (itemIndex != -1) {
            notifyItemChanged(itemIndex);
        }
    }

    public void updateItem(@IntRange(from = 0) final int index) {
        notifyItemChanged(index);
    }

    public void addItem(@NonNull final Item item) {
        addItem(item, false);
    }

    public void addItem(@NonNull final Item item, boolean notifyInserted) {
        mItems.add(item);
        if (notifyInserted) notifyItemInserted(mItems.size() - 1);
    }

    public void addItem(@IntRange(from = 0) final int index, @NonNull final Item item) {
        addItem(index, item, false);
    }

    public void addItem(@IntRange(from = 0) final int index, @NonNull final Item item, boolean notifyInserted) {
        mItems.add(index, item);
        if (notifyInserted) notifyItemInserted(index);
    }

    public void addItems(@NonNull final List<Item> items) {
        addItems(items, false);
    }

    public void addItems(@NonNull final List<Item> items, boolean notifyInserted) {
        mItems.addAll(items);
        if (notifyInserted) notifyItemRangeInserted(mItems.size() - items.size() - 1, items.size());
    }

    public void addItems(@IntRange(from = 0) final int index, @NonNull final List<Item> items) {
        addItems(index, items, false);
    }

    public void addItems(@IntRange(from = 0) final int index, @NonNull final List<Item> items, boolean notifyInserted) {
        mItems.addAll(index, items);
        if (notifyInserted) notifyItemRangeInserted(index, items.size());
    }

    public void swapItem(@IntRange(from = 0) final int firstIndex, @IntRange(from = 0) final int secondIndex) {
        swapItem(firstIndex, secondIndex, false);
    }

    public void swapItem(@IntRange(from = 0) final int firstIndex,
                         @IntRange(from = 0) final int secondIndex, boolean notifyMoved) {
        Collections.swap(mItems, firstIndex, secondIndex);
        if (notifyMoved) notifyItemMoved(firstIndex, secondIndex);
    }

    public Item replaceItem(@IntRange(from = 0) final int index, @NonNull final Item item) {
        return replaceItem(index, item, false);
    }

    public Item replaceItem(@IntRange(from = 0) final int index, @NonNull final Item item, boolean notifyChanged) {
        Item prevItem = mItems.set(index, item);
        if (notifyChanged) notifyItemChanged(index);
        return prevItem;
    }

    public boolean replaceItems(@NonNull final List<Item> items) {
        return replaceItems(items, false);
    }

    public boolean replaceItems(@NonNull final List<Item> items, boolean notifyChanged) {
        mItems.clear();
        boolean added = mItems.addAll(items);
        if (notifyChanged) notifyDataSetChanged();
        return added;
    }

    public Item removeItem(@IntRange(from = 0) final int index) {
        return removeItem(index, false);
    }

    public Item removeItem(@IntRange(from = 0) final int index, boolean notifyRemoved) {
        Item removedItem = mItems.remove(index);
        if (notifyRemoved) notifyItemRemoved(index);
        return removedItem;
    }

    public int removeItem(@NonNull final Item item) {
        return removeItem(item, false);
    }

    public int removeItem(@NonNull final Item item, boolean notifyRemoved) {
        int itemIndex = mItems.indexOf(item);
        if (itemIndex != -1) {
            mItems.remove(itemIndex);
            if (notifyRemoved) notifyItemRemoved(itemIndex);
        }
        return itemIndex;
    }

    public void clear() {
        clear(false);
    }

    public void clear(boolean notifyDataSetChanged) {
        mItems.clear();
        if (notifyDataSetChanged) notifyDataSetChanged();
    }

    public void sortItems(@NonNull final Comparator<Item> comparator) {
        sortItems(comparator, false);
    }

    public void sortItems(@NonNull final Comparator<Item> comparator, boolean notifyDataSetChanged) {
        Collections.sort(mItems, comparator);
        if (notifyDataSetChanged) notifyDataSetChanged();
    }
}
