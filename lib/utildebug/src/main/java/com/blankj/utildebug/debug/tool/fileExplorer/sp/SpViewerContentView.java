package com.blankj.utildebug.debug.tool.fileExplorer.sp;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utildebug.R;
import com.blankj.utildebug.base.rv.BaseItemAdapter;
import com.blankj.utildebug.base.rv.RecycleViewDivider;
import com.blankj.utildebug.base.view.BaseContentView;
import com.blankj.utildebug.base.view.BaseContentFloatView;
import com.blankj.utildebug.base.view.SearchEditText;
import com.blankj.utildebug.base.view.listener.OnRefreshListener;
import com.blankj.utildebug.debug.tool.fileExplorer.FileExplorerFloatView;

import java.io.File;
import java.util.List;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/09/09
 *     desc  :
 * </pre>
 */
public class SpViewerContentView extends BaseContentView<FileExplorerFloatView> {

    private File                    mFile;
    private BaseItemAdapter<SpItem> mAdapter;
    private List<SpItem>            mSrcItems;
    private String                  mSpName;
    private SPUtils                 mSPUtils;

    private TextView       spViewTitle;
    private SearchEditText spViewSearchEt;
    private RecyclerView   spViewRv;

    public static void show(FileExplorerFloatView floatView, File file) {
        new SpViewerContentView(file).attach(floatView, true);
    }

    public SpViewerContentView(File file) {
        mFile = file;
        mSpName = FileUtils.getFileNameNoExtension(mFile);
        mSPUtils = SPUtils.getInstance(mSpName);
    }

    @Override
    public int bindLayout() {
        return R.layout.du_debug_file_explorer_sp;
    }

    @Override
    public void onAttach() {
        spViewTitle = findViewById(R.id.spViewTitle);
        spViewSearchEt = findViewById(R.id.spViewSearchEt);
        spViewRv = findViewById(R.id.spViewRv);

        spViewTitle.setText(mSpName);

        mAdapter = new BaseItemAdapter<>();
        mSrcItems = SpItem.getSpItems(mSPUtils);
        mAdapter.setItems(mSrcItems);
        spViewRv.setAdapter(mAdapter);
        spViewRv.setLayoutManager(new LinearLayoutManager(getContext()));
        spViewRv.addItemDecoration(new RecycleViewDivider(getContext(), RecycleViewDivider.VERTICAL, R.drawable.du_shape_divider));

        spViewSearchEt.setOnTextChangedListener(new SearchEditText.OnTextChangedListener() {
            @Override
            public void onTextChanged(String text) {
                mAdapter.setItems(SpItem.filterItems(mSrcItems, text));
                mAdapter.notifyDataSetChanged();
            }
        });

        setOnRefreshListener(spViewRv, new OnRefreshListener() {
            @Override
            public void onRefresh(BaseContentFloatView floatView) {
                mSrcItems = SpItem.getSpItems(mSPUtils);
                mAdapter.setItems(mSrcItems);
                mAdapter.notifyDataSetChanged();
                spViewSearchEt.reset();
                floatView.closeRefresh();
            }
        });
    }
}
