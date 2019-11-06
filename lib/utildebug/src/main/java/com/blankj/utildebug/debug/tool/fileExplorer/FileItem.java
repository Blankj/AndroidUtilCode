package com.blankj.utildebug.debug.tool.fileExplorer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ClickUtils;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.SDCardUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.UriUtils;
import com.blankj.utildebug.R;
import com.blankj.utildebug.base.rv.BaseItem;
import com.blankj.utildebug.base.rv.ItemViewHolder;
import com.blankj.utildebug.base.view.FloatToast;
import com.blankj.utildebug.debug.tool.fileExplorer.image.ImageViewer;
import com.blankj.utildebug.debug.tool.fileExplorer.sp.SpViewerContentView;
import com.blankj.utildebug.helper.FileHelper;
import com.blankj.utildebug.helper.ImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/09/05
 *     desc  :
 * </pre>
 */
public class FileItem extends BaseItem<FileItem> {

    private static final ArrayList<FileItem> EMPTY = CollectionUtils.newArrayList(new FileItem());

    private FileItem mParent;
    private File     mFile;
    private String   mName;
    private boolean  isSdcard;

    private RelativeLayout fileContentRl;
    private ImageView      fileTypeIv;
    private TextView       fileNameTv;
    private TextView       fileInfoTv;
    private TextView       fileMenuDeleteTv;

    public FileItem(File file, String name) {
        this(file, name, false);
    }

    public FileItem(File file, String name, boolean isSdcard) {
        super(R.layout.du_item_file);
        mFile = file;
        mName = name;
        this.isSdcard = isSdcard;
    }

    public FileItem(FileItem parent, File file) {
        super(R.layout.du_item_file);
        mParent = parent;
        mFile = file;
        mName = file.getName();
    }

    public FileItem() {
        super(R.layout.du_item_empty);
    }

    @Override
    public void bind(@NonNull ItemViewHolder holder, int position) {
        if (isViewType(R.layout.du_item_empty)) return;
        fileContentRl = holder.findViewById(R.id.fileContentRl);
        fileTypeIv = holder.findViewById(R.id.fileTypeIv);
        fileNameTv = holder.findViewById(R.id.fileNameTv);
        fileInfoTv = holder.findViewById(R.id.fileInfoTv);
        fileMenuDeleteTv = holder.findViewById(R.id.fileMenuDeleteTv);

        ClickUtils.applyPressedBgDark(fileContentRl);
        ClickUtils.applyPressedBgDark(fileMenuDeleteTv);

        fileNameTv.setText(mName);

        fileMenuDeleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean delete = FileUtils.delete(mFile);
                if (delete) {
                    getAdapter().removeItem(FileItem.this, true);
                    if (getAdapter().getItems().isEmpty()) {
                        getAdapter().addItem(new FileItem());
                        getAdapter().notifyDataSetChanged();
                        v.getRootView().findViewById(R.id.fileExplorerSearchEt).setVisibility(View.GONE);
                    }
                } else {
                    FloatToast.showLong(FloatToast.WARNING, "Delete failed!");
                }
            }
        });
        fileMenuDeleteTv.setVisibility(mParent == null ? View.GONE : View.VISIBLE);

        if (mFile.isDirectory()) {
            fileTypeIv.setImageResource(R.drawable.du_ic_debug_file_explorer);
            fileInfoTv.setText(String.format("%s  %s", StringUtils.getString(R.string.du_file_item_num, CollectionUtils.size(mFile.list())), TimeUtils.millis2String(mFile.lastModified(), "yyyy.MM.dd")));
            fileContentRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if (isSdcard) {
                        PermissionUtils.permission(PermissionConstants.STORAGE)
                                .callback(new PermissionUtils.SimpleCallback() {
                                    @Override
                                    public void onGranted() {
                                        FileExplorerFloatView floatView = (FileExplorerFloatView) v.getRootView();
                                        FileContentView.show(floatView, FileItem.this);
                                    }

                                    @Override
                                    public void onDenied() {
                                        FloatToast.showShort("Permission of storage denied!");
                                    }
                                })
                                .request();
                    } else {
                        FileExplorerFloatView floatView = (FileExplorerFloatView) v.getRootView();
                        FileContentView.show(floatView, FileItem.this);
                    }
                }
            });
        } else {
            fileInfoTv.setText(String.format("%s  %s", FileUtils.getSize(mFile), TimeUtils.millis2String(mFile.lastModified(), "yyyy.MM.dd")));

            @FileHelper.FileType int fileType = FileHelper.getFileType(mFile);
            if (fileType == FileHelper.IMAGE) {
                ImageLoader.load(mFile, fileTypeIv);
                fileContentRl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FileExplorerFloatView floatView = (FileExplorerFloatView) v.getRootView();
                        ImageViewer.show(floatView, mFile);
                    }
                });
            } else if (fileType == FileHelper.UTF8) {
                fileTypeIv.setImageResource(R.drawable.du_ic_item_file_utf8);
            } else if (fileType == FileHelper.SP) {
                fileTypeIv.setImageResource(R.drawable.du_ic_item_file_sp);
                fileContentRl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FileExplorerFloatView floatView = (FileExplorerFloatView) v.getRootView();
                        SpViewerContentView.show(floatView, mFile);
                    }
                });
            } else {
                fileContentRl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setData(UriUtils.file2Uri(mFile));
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        ActivityUtils.startActivity(intent);
                    }
                });
                fileTypeIv.setImageResource(R.drawable.du_ic_item_file_default);
            }
        }
    }

    public File getFile() {
        return mFile;
    }

    public static List<FileItem> getFileItems(final FileItem parent) {
        if (parent == null) return getFileItems();
        List<File> files = FileUtils.listFilesInDir(parent.getFile(), new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                if (o1.isDirectory() && o2.isFile()) {
                    return -1;
                } else if (o1.isFile() && o2.isDirectory()) {
                    return 1;
                } else {
                    return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
                }
            }
        });
        return (List<FileItem>) CollectionUtils.collect(files, new CollectionUtils.Transformer<File, FileItem>() {
            @Override
            public FileItem transform(File input) {
                return new FileItem(parent, input);
            }
        });
    }

    private static List<FileItem> getFileItems() {
        List<FileItem> fileItems = new ArrayList<>();
        String internalAppDataPath = PathUtils.getInternalAppDataPath();
        if (!StringUtils.isEmpty(internalAppDataPath)) {
            File internalDataFile = new File(internalAppDataPath);
            if (internalDataFile.exists()) {
                fileItems.add(new FileItem(internalDataFile, "internal"));
            }
        }
        String externalAppDataPath = PathUtils.getExternalAppDataPath();
        if (!StringUtils.isEmpty(externalAppDataPath)) {
            File externalDataFile = new File(externalAppDataPath);
            if (externalDataFile.exists()) {
                fileItems.add(new FileItem(externalDataFile, "external"));
            }
        }
        List<String> mountedSDCardPath = SDCardUtils.getMountedSDCardPath();
        if (!mountedSDCardPath.isEmpty()) {
            for (int i = 0; i < mountedSDCardPath.size(); i++) {
                String path = mountedSDCardPath.get(i);
                File sdPath = new File(path);
                if (sdPath.exists()) {
                    fileItems.add(new FileItem(sdPath, "sdcard" + i + "_" + sdPath.getName(), true));
                }
            }
        }
        return fileItems;
    }

    public static List<FileItem> filterItems(List<FileItem> items, final String key) {
        return (List<FileItem>) CollectionUtils.select(items, new CollectionUtils.Predicate<FileItem>() {
            @Override
            public boolean evaluate(FileItem item) {
                return item.mName.toLowerCase().contains(key.toLowerCase());
            }
        });
    }

    public static boolean isEmptyItems(List<FileItem> items) {
        return EMPTY == items;
    }
}
