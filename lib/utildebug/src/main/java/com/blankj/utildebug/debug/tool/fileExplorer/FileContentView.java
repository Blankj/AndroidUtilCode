package com.blankj.utildebug.debug.tool.fileExplorer;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.blankj.utildebug.R;
import com.blankj.utildebug.base.rv.BaseItemAdapter;
import com.blankj.utildebug.base.rv.RecycleViewDivider;
import com.blankj.utildebug.base.view.BaseContentFloatView;
import com.blankj.utildebug.base.view.BaseContentView;
import com.blankj.utildebug.base.view.SearchEditText;
import com.blankj.utildebug.base.view.listener.OnBackListener;
import com.blankj.utildebug.base.view.listener.OnRefreshListener;

import java.util.List;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/09/06
 *     desc  :
 * </pre>
 */
public class FileContentView extends BaseContentView<FileExplorerFloatView> {

    private FileItem                  mParent;
    private BaseItemAdapter<FileItem> mAdapter;
    private List<FileItem>            mSrcItems;

    private SearchEditText fileExplorerSearchEt;
    private RecyclerView   fileExplorerRv;

    public static void show(FileExplorerFloatView floatView) {
        new FileContentView(null).attach(floatView, true);
    }

    public static void show(FileExplorerFloatView floatView, FileItem fileItem) {
        new FileContentView(fileItem).attach(floatView, true);
    }

    public FileContentView(FileItem parent) {
        mParent = parent;
        mSrcItems = FileItem.getFileItems(mParent);
    }

    @Override
    public int bindLayout() {
        return R.layout.du_debug_file_explorer;
    }

    @Override
    public void onAttach() {
        fileExplorerSearchEt = findViewById(R.id.fileExplorerSearchEt);
        fileExplorerRv = findViewById(R.id.fileExplorerRv);

        if (FileItem.isEmptyItems(mSrcItems)) {
            fileExplorerSearchEt.setVisibility(GONE);
        }

        mAdapter = new BaseItemAdapter<>();
        mAdapter.setItems(mSrcItems);
        fileExplorerRv.setAdapter(mAdapter);
        fileExplorerRv.setLayoutManager(new LinearLayoutManager(getContext()));
        fileExplorerRv.addItemDecoration(new RecycleViewDivider(getContext(), RecycleViewDivider.VERTICAL, R.drawable.du_shape_file_divider));

        fileExplorerSearchEt.setOnTextChangedListener(new SearchEditText.OnTextChangedListener() {
            @Override
            public void onTextChanged(String text) {
                mAdapter.setItems(FileItem.filterItems(mSrcItems, text));
                mAdapter.notifyDataSetChanged();
            }
        });

        setOnRefreshListener(fileExplorerRv, new OnRefreshListener() {
            @Override
            public void onRefresh(BaseContentFloatView floatView) {
                mSrcItems = FileItem.getFileItems(mParent);
                mAdapter.setItems(mSrcItems);
                mAdapter.notifyDataSetChanged();
                fileExplorerSearchEt.reset();
                floatView.closeRefresh();
            }
        });
    }

    @Override
    public void onBack() {
        super.onBack();
        if (mParent != null) {
            mParent.update();
        }
    }
}
