package com.blankj.subutil.util;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.ScanCallback;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;

import com.blankj.utilcode.util.ConvertUtils;

import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <a href="https://www.bluetooth.com/">SIG</a> BT Util on Android platform。
 *
 * <pre>
 *     author: bangbang.S
 *     time  : 2020/12/11
 *     desc  : 蓝牙相关工具类
 * </pre>
 */
public class BtUtils {
    private static final String TAG = "BtUtils";

    //https://www.bluetooth.com/specifications/assigned-numbers/service-discovery
    public static final UUID BASE_UUID = toUuid("00000000-0000-1000-8000-00805F9B34FB");
    public static final long BASE_UUID_LEAST = BASE_UUID.getLeastSignificantBits();
    public static final long BASE_UUID_MOST = BASE_UUID.getMostSignificantBits();

    private static final Pattern PATTERN_16_UUID = Pattern.compile("0000([0-9A-F]{4})-0000-1000-8000-00805F9B34FB");
    private static final Pattern PATTERN_32_UUID = Pattern.compile("([0-9A-F]{8})-0000-1000-8000-00805F9B34FB");

    //https://www.bluetooth.com/specifications/gatt/descriptors
    public static final UUID DESCRIPTOR_CHARA_USER_DESC = toUuid("2901");
    public static final UUID DESCRIPTOR_CHARA_CONFIG = toUuid("2902");

    /**
     * convert  uuid string short (16bits or full form) to UUID
     * @param uuid
     * @return
     *
     * @see <a href="https://www.bluetooth.com/specifications/assigned-numbers/service-discovery">service-discovery</a>
     */
    static public final UUID toUuid(String uuid) {
        return UUID.fromString(uuid.length() == 4
                ? "0000" + uuid + "-0000-1000-8000-00805f9b34fb"
                : uuid);
    }

    /**
     * convert uuid to short form (16bits or 32bits)
     * @param uuid
     * @return short form uuid string if possible, other empty string, i.e. "".
     */
    public static String toShortUuidStringOrEmpty(String uuid) {
        uuid = uuid.toUpperCase();
        String shortUuid = "";
        Matcher m = PATTERN_16_UUID.matcher(uuid);
        if (m.matches()) {
            shortUuid = m.group(1);
        } else {
            m = PATTERN_32_UUID.matcher(uuid);
            if (m.matches()) {
                shortUuid = m.group(1);
            }
        }

        return shortUuid;
    }

    /**
     * convert uuid to short form (16bits or 32bits)
     * @param uuid
     * @return short form uuid string if possible, other original uuid string.
     */
    public static String toShortUuidString(UUID uuid) {
        return toShortUuidString(uuid.toString());
    }

    /**
     * convert uuid to short form (16bits or 32bits)
     * @param uuid
     * @return short form uuid string if possible, other original uuid string.
     */
    public static String toShortUuidString(String uuid) {
        String shortUuid = toShortUuidStringOrEmpty(uuid);
        if (TextUtils.isEmpty(shortUuid)) {
            shortUuid = uuid;
        }

        return shortUuid;
    }

    /**
     *
     * convert status param in
     * {@link android.bluetooth.BluetoothGattCallback#onConnectionStateChange(BluetoothGatt, int, int) onConnectionStateChange}
     * callback to String
     *
     * @param status
     * @return
     *
     * @see <a href="https://android.googlesource.com/platform/external/bluetooth/bluedroid/+/android-cts-5.1_r17/stack/include/gatt_api.h">gatt_api.h</a>
     * @See {@link #toGattOperateStatusString(int)}
     */
    public static final String toGattConnectionStatusString(int status) {
        String statusStr = null;
        switch (status) {
            case BluetoothGatt.GATT_SUCCESS:
                statusStr = "GATT_SUCCESS";
                break;

            case 0x01:
                statusStr = "GATT_CONN_L2C_FAILURE";
                break;
            case 0x08:
                statusStr = "GATT_CONN_TIMEOUT";
                break;
            case 0x13:
                statusStr = "GATT_CONN_TERMINATE_PEER_USER";
                break;
            case 0x16:
                statusStr = "GATT_CONN_TERMINATE_LOCAL_HOST";
                break;
            case 0x003E:
                statusStr = "GATT_CONN_FAIL_ESTABLISH";
                break;
            case 0x0022:
                statusStr = "GATT_CONN_LMP_TIMEOUT";
                break;
            case 0x0085:
                statusStr = "GATT_ERROR";
                break;
            case 0x0100:
                statusStr = "GATT_CONN_CANCEL";
                break;
            default:
                statusStr = "UNKNOWN_STATUS";
        }

        statusStr += "[" + status + " 0x" + String.format("%1$04X", status) + "]";

        return statusStr;
    }



    /**
     * convert status param in
     * all callbacks in {@link android.bluetooth.BluetoothGattCallback} except
     * {@link android.bluetooth.BluetoothGattCallback#onConnectionStateChange(BluetoothGatt, int, int) onConnectionStateChange}
     * to String
     *
     * @param status
     * @return
     *
     * @See {@link #toGattOperateStatusString(int)}
     * @see <a href="https://android.googlesource.com/platform/external/bluetooth/bluedroid/+/android-cts-5.1_r17/stack/include/gatt_api.h">gatt_api.h</a>
     * @see <a href="http://blog.csdn.net/jasonwang18/article/details/73312283">错误码</a>
     */
    public static final String toGattOperateStatusString(int status) {
        String statusStr = null;
        switch (status) {
            case BluetoothGatt.GATT_SUCCESS:
                statusStr = "GATT_SUCCESS";
                break;
            case BluetoothGatt.GATT_CONNECTION_CONGESTED:
                statusStr = "GATT_CONNECTION_CONGESTED";
                break;
            case BluetoothGatt.GATT_FAILURE:
                statusStr = "GATT_FAILURE";
                break;
            case BluetoothGatt.GATT_INSUFFICIENT_AUTHENTICATION:
                statusStr = "GATT_INSUFFICIENT_AUTHENTICATION";
                break;
            case BluetoothGatt.GATT_INSUFFICIENT_ENCRYPTION:
                statusStr = "GATT_INSUFFICIENT_ENCRYPTION";
                break;
            case BluetoothGatt.GATT_INVALID_ATTRIBUTE_LENGTH:
                statusStr = "GATT_INVALID_ATTRIBUTE_LENGTH";
                break;
            case BluetoothGatt.GATT_INVALID_OFFSET:
                statusStr = "GATT_INVALID_OFFSET";
                break;
            case BluetoothGatt.GATT_READ_NOT_PERMITTED:
                statusStr = "GATT_READ_NOT_PERMITTED";
                break;
            case BluetoothGatt.GATT_REQUEST_NOT_SUPPORTED:
                statusStr = "GATT_SUCCESS";
                break;
            case BluetoothGatt.GATT_WRITE_NOT_PERMITTED:
                statusStr = "GATT_WRITE_NOT_PERMITTED";
                break;

            default:
                statusStr += "UNKNOWN_STATUS[0x" + String.format("%1$04X", status) + "]";
                break;
        }

        if (statusStr.contains("UNKNOWN")) {
            switch (status) {

                //http://androidxref.com/4.4_r1/xref/external/bluetooth/bluedroid/stack/include/gatt_api.h
                case 0x0001:
                    statusStr = "GATT_INVALID_HANDLE";
                    break;
                case 0x0002:
                    statusStr = "GATT_READ_NOT_PERMIT";
                    break;
                case 0x0003:
                    statusStr = "GATT_WRITE_NOT_PERMIT";
                    break;
                case 0x0004:
                    statusStr = "GATT_INVALID_PDU";
                    break;
                case 0x0005:
                    statusStr = "GATT_INSUF_AUTHENTICATION";
                    break;
                case 0x0006:
                    statusStr = "GATT_REQ_NOT_SUPPORTED";
                    break;
                case 0x0007:
                    statusStr = "GATT_INVALID_OFFSET";
                    break;
                case 0x0008:
                    statusStr = "GATT_INSUF_AUTHORIZATION";
                    break;
                case 0x0009:
                    statusStr = "GATT_PREPARE_Q_FULL";
                    break;
                case 0x000a:
                    statusStr = "GATT_NOT_FOUND";
                    break;
                case 0x000b:
                    statusStr = "GATT_NOT_LONG";
                    break;
                case 0x000C:
                    statusStr = "GATT_INSUF_KEY_SIZE";
                    break;
                case 0x000D:
                    statusStr = "GATT_INVALID_ATTR_LEN";
                    break;
                case 0x000E:
                    statusStr = "GATT_ERR_UNLIKELY";
                    break;
                case 0x000F:
                    statusStr = "GATT INSUF ENCRYPTION";
                    break;
                case 0x0010:
                    statusStr = "GATT_UNSUPPORT_GRP_TYPE";
                    break;
                case 0x0011:
                    statusStr = "GATT_INSUF_RESOURCE";
                    break;
                case 0x13:
                    statusStr = "GATT_CONN_TERMINATE_PEER_USER";
                    break;
                case 0x16:
                    statusStr = "GATT_CONN_TERMINATE_LOCAL_HOST";
                    break;

                case 0x003A:
                    statusStr =  "GATT CONTROLLER BUSY";
                    break;

                case 0x0080:
                    statusStr = "GATT_NO_RESOURCES";
                    break;
                case 0x0081:
                    statusStr = "GATT_INTERNAL_ERROR";
                    break;
                case 0x0082:
                    statusStr = "GATT_WRONG_STATE";
                    break;
                case 0x0083:
                    statusStr = "GATT_DB_FULL";
                    break;
                case 0x0084:
                    statusStr = "GATT_BUSY";
                    break;
                case 0x0085:
                    statusStr = "GATT_ERROR";
                    break;
                case 0x0086:
                    statusStr = "GATT_CMD_STARTED";
                    break;
                case 0x0087:
                    statusStr = "GATT_ILLEGAL_PARAMETER";
                    break;
                case 0x0088:
                    statusStr = "GATT_PENDING";
                    break;
                case 0x0089:
                    statusStr = "GATT_AUTH_FAIL";
                    break;
                case 0x008a:
                    statusStr = "GATT_MORE";
                    break;
                case 0x008b:
                    statusStr = "GATT_INVALID_CFG";
                    break;
                case 0x008c:
                    statusStr = "GATT_SERVICE_STARTED";
                    break;
                case 0x008D:
                    statusStr = "GATT_ENCRYPED_NO_MITM";
                    break;
                case 0x008e:
                    statusStr = "GATT_NOT_ENCRYPTED";
                    break;
                case 0x008f:
                    statusStr = "GATT CONGESTED";
                    break;

                case 0x003E:
                    statusStr = "GATT_CONN_FAIL_ESTABLISH";
                    break;
                case 0x0022:
                    statusStr = "GATT_CONN_LMP_TIMEOUT ";
                    break;
                case 0x0100:
                    statusStr = "GATT_CONN_CANCEL";
                    break;
                case 0x0101:
                    statusStr = "TOO MANY OPEN CONNECTIONS";
                    break;
            }
            statusStr += "[" + status + " 0x" + String.format("%1$04X", status) + "]";
//            statusStr += " see android source code gatt_api.h";
        }

        return statusStr;
    }

    /**
     * convert BluetoothDevice.DEVICE_TYPE_ to String
     * @param type
     * @return
     */
    public static final String toBluetoothDeviceTypeString(int type) {
        String typeStr = "unknown type[" + type + "]";
        switch (type) {
            case BluetoothDevice.DEVICE_TYPE_CLASSIC:
                typeStr = "DEVICE_TYPE_CLASSIC";
                break;
            case BluetoothDevice.DEVICE_TYPE_LE:
                typeStr = "DEVICE_TYPE_LE";
                break;
            case BluetoothDevice.DEVICE_TYPE_DUAL:
                typeStr = "DEVICE_TYPE_DUAL";
                break;
            case BluetoothDevice.DEVICE_TYPE_UNKNOWN:
                typeStr = "DEVICE_TYPE_UNKNOWN";
                break;
            default:
                typeStr = "UNKNOWN_DEVICE_TYPE[" + type + "]";
        }

        return typeStr;
    }

    /**
     * convert BluetoothGatt.CONNECTION_PRIORITY_ to String
     * @param priority
     * @return
     */
    public static final String toConnectionPriorityString(int priority) {
        String priorityStr = "unknown priority[" + priority + "]";
        switch (priority) {
            case BluetoothGatt.CONNECTION_PRIORITY_BALANCED:
                priorityStr = "CONNECTION_PRIORITY_BALANCED";
                break;
            case BluetoothGatt.CONNECTION_PRIORITY_HIGH:
                priorityStr = "CONNECTION_PRIORITY_HIGH";
                break;
            case BluetoothGatt.CONNECTION_PRIORITY_LOW_POWER:
                priorityStr = "CONNECTION_PRIORITY_LOW_POWER";
                break;
            default:
                priorityStr = "UNKNOWN_PRIORITY[" + priority + "]";
        }
        return priorityStr;
    }

    /**
     * convert BluetoothDevice.BOND_ to String
     * @param bondState
     * @return
     */
    public static final String toBluetoothDeviceBondStateString(int bondState) {
        String bondStr = "unknown state[" + bondState + "]";
        switch (bondState) {
            case BluetoothDevice.BOND_BONDED:
                bondStr = "BOND_BONDED";
                break;
            case BluetoothDevice.BOND_BONDING:
                bondStr = "BOND_BONDING";
                break;
            case BluetoothDevice.BOND_NONE:
                bondStr = "BOND_NONE";
                break;
            default:
                bondStr = "UNKNOWN_BOND_TYPE[" + bondState + "]";
        }
        return bondStr;
    }

    /**
     * convert BluetoothGattService.SERVICE_ to String
     * @param serviceType
     * @return
     */
    public static final String toBluetoothGattServiceType(int serviceType) {
        String typeStr = null;
        switch (serviceType) {
            case BluetoothGattService.SERVICE_TYPE_PRIMARY:
                typeStr = "PRIMARY";
                break;
            case BluetoothGattService.SERVICE_TYPE_SECONDARY:
                typeStr = "SECONDARY";
                break;
            default:
                typeStr = "UNKNOWN_SERVICE_TYPE[" + serviceType + "]";
        }

        return typeStr;
    }

    /**
     * convert BluetoothGattCharacteristic.WRITE_TYPE_ to String
     * @param writeType
     * @return
     */
    public static final String toCharacteristicWriteTypeStr(int writeType) {
        String w = null;
        switch (writeType) {
            case BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT:
                w = "WRITE_TYPE_DEFAULT";
                break;
            case BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE:
                w = "WRITE_TYPE_NO_RESPONSE";
                break;
            case BluetoothGattCharacteristic.WRITE_TYPE_SIGNED:
                w = "WRITE_TYPE_SIGNED";
                break;
            default:
                w = "UNKNOWN_WRITE_TYPE[" + writeType + "]";
        }
        return w;
    }

    /**
     * convert BluetoothGattCharacteristic properties (man contain multi properties) to String
     * @param properties
     * @return
     */
    public static final String toPropertiesStr(int properties) {
        String p = "";
        int i = 0;
        while (i < Integer.SIZE) {
            String pro = toPropertyStr(properties & 1 << i);
            if (!TextUtils.isEmpty(pro)) {
                p += pro + " ";
            }

            i++;
        }

        p = p.trim();
        return p;
    }

    /**
     * convert BluetoothGattCharacteristic.PROPERTY_ to String
     * @param property
     * @return
     */
    public static final String toPropertyStr(int property) {
        String p = null;
        switch (property) {
            case BluetoothGattCharacteristic.PROPERTY_BROADCAST:
                p = "BROADCAST";
                break;
            case BluetoothGattCharacteristic.PROPERTY_EXTENDED_PROPS:
                p = "EXTENDED_PROPS";
                break;
            case BluetoothGattCharacteristic.PROPERTY_INDICATE:
                p = "INDICATE";
                break;
            case BluetoothGattCharacteristic.PROPERTY_NOTIFY:
                p = "NOTIFY";
                break;
            case BluetoothGattCharacteristic.PROPERTY_READ:
                p = "READ";
                break;
            case BluetoothGattCharacteristic.PROPERTY_SIGNED_WRITE:
                p = "SIGNED_WRITE";
                break;
            case BluetoothGattCharacteristic.PROPERTY_WRITE:
                p = "WRITE";
                break;
            case BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE:
                p = "WRITE_NO_RESPONSE";
                break;
            default:
                p = "UNKNOWN_PROPERTY[" + property + "]";
        }
        return p;
    }

    /**
     * convert BluetoothGattDescriptor.PERMISSION_ to String
     * @param permission
     * @return
     */
    public static final String toDescriptorPermissionStr(int permission) {
        String p = null;
        switch (permission) {
            case BluetoothGattDescriptor.PERMISSION_READ:
                p = "READ";
                break;
            case BluetoothGattDescriptor.PERMISSION_READ_ENCRYPTED:
                p = "READ_ENCRYPTED";
                break;
            case BluetoothGattDescriptor.PERMISSION_READ_ENCRYPTED_MITM:
                p = "READ_ENCRYPTED_MITM";
                break;
            case BluetoothGattDescriptor.PERMISSION_WRITE:
                p = "WRITE";
                break;
            case BluetoothGattDescriptor.PERMISSION_WRITE_ENCRYPTED:
                p = "WRITE_ENCRYPTED";
                break;
            case BluetoothGattDescriptor.PERMISSION_WRITE_SIGNED:
                p = "WRITE_SIGNED";
                break;
            case BluetoothGattDescriptor.PERMISSION_WRITE_ENCRYPTED_MITM:
                p = "READ_ENCRYPTED_MITM";
                break;
            case BluetoothGattDescriptor.PERMISSION_WRITE_SIGNED_MITM:
                p = "WRITE_SIGNED_MITM";
                break;
            default:
                p = "UNKNOWN_PERMISSION[" + permission + "]";
        }

        return p;
    }

    /**
     * convert BluetoothGattCharacteristic.PERMISSION_ to String
     * @param permission
     * @return
     */
    public static final String toCharacterPermissionStr(int permission) {
        String p = null;
        switch (permission) {
            case BluetoothGattCharacteristic.PERMISSION_READ:
                p = "READ";
                break;
            case BluetoothGattCharacteristic.PERMISSION_READ_ENCRYPTED:
                p = "READ_ENCRYPTED";
                break;
            case BluetoothGattCharacteristic.PERMISSION_READ_ENCRYPTED_MITM:
                p = "READ_ENCRYPTED_MITM";
                break;
            case BluetoothGattCharacteristic.PERMISSION_WRITE:
                p = "WRITE";
                break;
            case BluetoothGattCharacteristic.PERMISSION_WRITE_ENCRYPTED:
                p = "WRITE_ENCRYPTED";
                break;
            case BluetoothGattCharacteristic.PERMISSION_WRITE_SIGNED:
                p = "WRITE_SIGNED";
                break;
            case BluetoothGattCharacteristic.PERMISSION_WRITE_ENCRYPTED_MITM:
                p = "PERMISSION_WRITE_ENCRYPTED_MITM";
                break;
            case BluetoothGattCharacteristic.PERMISSION_WRITE_SIGNED_MITM:
                p = "WRITE_SIGNED_MITM";
                break;
            default:
                p = "UNKNOWN_PERMISSION[" + permission + "]";
        }

        return p;
    }

    private static final String makeIndent(int indent) {
        String indentStr = "";
        for (int i = 0; i < indent; i++) {
            indentStr += " ";
        }
        return indentStr;
    }

    /**
     * convert ScanCallback.SCAN_FAILED_ to String
     * @param reason
     * @return
     */
    public static String toScanFailedReasonString(int reason){
        String reasonStr = null;
        switch (reason) {
            case ScanCallback.SCAN_FAILED_ALREADY_STARTED:
                reasonStr = "SCAN_FAILED_ALREADY_STARTED";
                break;
            case ScanCallback.SCAN_FAILED_APPLICATION_REGISTRATION_FAILED:
                reasonStr = "SCAN_FAILED_APPLICATION_REGISTRATION_FAILED";
                break;
            case ScanCallback.SCAN_FAILED_FEATURE_UNSUPPORTED:
                reasonStr = "SCAN_FAILED_FEATURE_UNSUPPORTED";
                break;
            case ScanCallback.SCAN_FAILED_INTERNAL_ERROR:
                reasonStr = "SCAN_FAILED_INTERNAL_ERROR";
                break;
            default:
                reasonStr = "UNKNOWN_REASON[" + reason + "]";
        }
        return reasonStr;
    }

    /**
     * convert BluetoothAdapter.STATE_ to String
     * @param state
     * @return
     */
    public static String toBluetoothAdapterStateString(int state){
        String stateStr = null;
        switch (state) {
            case BluetoothAdapter.STATE_ON:
                stateStr = "STATE_ON";
                break;
            case BluetoothAdapter.STATE_OFF:
                stateStr = "STATE_OFF";
                break;
            case BluetoothAdapter.STATE_TURNING_OFF:
                stateStr = "STATE_TURNING_OFF";
                break;
            case BluetoothAdapter.STATE_TURNING_ON:
                stateStr = "STATE_TURNING_ON";
                break;
            case 14://BluetoothAdapter.STATE_BLE_TURNING_ON:
                stateStr = "STATE_BLE_TURNING_ON";
                break;
            case 15://BluetoothAdapter.STATE_BLE_ON:
                stateStr = "STATE_BLE_ON";
                break;
            case 16://BluetoothAdapter.STATE_BLE_TURNING_OFF:
                stateStr = "STATE_BLE_TURNING_OFF";
                break;
            default:
                stateStr = "UNKNOWN_STATE[" + state + "]";
                break;
        }

        return stateStr;
    }

    /**
     * convert BluetoothAdapter.STATE_ to String
     * @param state
     * @return
     */
    public static String toBluetoothAdapterConnectionStateString(int state){
        String stateStr = null;
        switch (state) {
            case BluetoothAdapter.STATE_CONNECTED:
                stateStr = "STATE_CONNECTED";
                break;
            case BluetoothAdapter.STATE_CONNECTING:
                stateStr = "STATE_CONNECTING";
                break;
            case BluetoothAdapter.STATE_DISCONNECTING:
                stateStr = "STATE_DISCONNECTING";
                break;
            case BluetoothAdapter.STATE_DISCONNECTED:
                stateStr = "STATE_DISCONNECTED";
                break;
            default:
                stateStr = "UNKNOWN_STATE[" + state + "]";
        }
        return stateStr;
    }

    /**
     * convert BluetoothProfile.STATE_ to String
     *
     * @param state
     * @return
     */
    public static final String toBluetoothProfileStateString(int state) {
        String stateStr = null;
        switch (state) {
            case BluetoothProfile.STATE_CONNECTED:
                stateStr = "STATE_CONNECTED";
                break;
            case BluetoothProfile.STATE_CONNECTING:
                stateStr = "STATE_CONNECTING";
                break;
            case BluetoothProfile.STATE_DISCONNECTED:
                stateStr = "STATE_DISCONNECTED";
                break;
            case BluetoothProfile.STATE_DISCONNECTING:
                stateStr = "STATE_DISCONNECTING";
                break;
            default:
                stateStr = "UNKNOWN_STATE[" + state + "]";
        }

        return stateStr;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static final void dump(StringBuilder builder, int indent, BluetoothGatt gatt) {
        if (null == gatt) {
            return;
        }

        List<BluetoothGattService> services = gatt.getServices();
        for (int i = 0; i < services.size(); i++) {
            BluetoothGattService service = services.get(i);
            dump(builder, indent + 1, service);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static final void dump(StringBuilder builder, int indent, BluetoothGattService service) {
        if (null == service) {
            return;
        }

        appendln(builder, makeIndent(indent) + "Service:" + service);
        appendln(builder, makeIndent(indent + 1) + "uuid       :" + toShortUuidString(service.getUuid()));
        appendln(builder, makeIndent(indent + 1) + "type       :" + toBluetoothGattServiceType(service.getType()));
        appendln(builder, makeIndent(indent + 1) + "instance id:" + service.getInstanceId());

        List<BluetoothGattCharacteristic> chars = service.getCharacteristics();
        for (int j = 0; j < chars.size(); j++) {
            BluetoothGattCharacteristic c = chars.get(j);
            dump(builder, indent + 1, c);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static final void dump(StringBuilder builder, int indent, BluetoothGattCharacteristic c) {
        if (null == c) {
            return;
        }

        appendln(builder, makeIndent(indent) + "Character:" + c);
        appendln(builder, makeIndent(indent + 1) + "uuid       :" + toShortUuidString(c.getUuid()));
        appendln(builder, makeIndent(indent + 1) + "values     :" + ConvertUtils.bytes2HexString(c.getValue()));
        appendln(builder, makeIndent(indent + 1) + "writeType  :" + toCharacteristicWriteTypeStr(c.getWriteType()));
        appendln(builder, makeIndent(indent + 1) + "permission :" + toCharacterPermissionStr(c.getPermissions()));
        appendln(builder, makeIndent(indent + 1) + "properties :" + toPropertiesStr(c.getProperties()));
        appendln(builder, makeIndent(indent + 1) + "instance id:" + c.getInstanceId());

        List<BluetoothGattDescriptor> ds = c.getDescriptors();
        for (int j = 0; j < ds.size(); j++) {
            BluetoothGattDescriptor d = ds.get(j);
            dump(builder, indent + 1, d);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static final void dump(StringBuilder builder, int indent, BluetoothGattDescriptor d) {
        if (null == d) {
            return;
        }

        appendln(builder, makeIndent(indent) + "descriptor:" + d);
        appendln(builder, makeIndent(indent + 1) + "uuid      :" + toShortUuidString(d.getUuid()));
        appendln(builder, makeIndent(indent + 1) + "values    :" + ConvertUtils.bytes2HexString(d.getValue()));
        appendln(builder, makeIndent(indent + 1) + "permission:" + toDescriptorPermissionStr(d.getPermissions()));
    }

    public static final void dumpBtIntentSmartly(StringBuilder builder, Intent intent){
        String action = intent.getAction();
        if (action.equals(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED)) {
            dumpIntent_ACTION_CONNECTION_STATE_CHANGED(builder, intent);
        } else if (action.equalsIgnoreCase(BluetoothAdapter.ACTION_STATE_CHANGED)){
            dumpIntent_ACTION_STATE_CHANGED(builder, intent);
        } else if (action.equalsIgnoreCase(BluetoothDevice.ACTION_ACL_CONNECTED)){
            dumpIntent_ACTION_ACL_CONNECTED(builder, intent);
        } else if (action.equalsIgnoreCase(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED)){
            dumpIntent_ACTION_ACL_DISCONNECT_REQUESTED(builder, intent);
        } else if (action.equalsIgnoreCase(BluetoothDevice.ACTION_ACL_DISCONNECTED)){
            dumpIntent_ACTION_ACL_DISCONNECTED(builder, intent);
        } else if (action.equalsIgnoreCase(BluetoothDevice.ACTION_BOND_STATE_CHANGED)){
            dumpIntent_ACTION_BOND_STATE_CHANGED(builder, intent);
        } else {
            dump(builder, 0, intent);
        }
    }

    public static void dumpIntent_ACTION_ACL_CONNECTED(StringBuilder builder, Intent intent) {
        assertAction(intent, BluetoothDevice.ACTION_ACL_CONNECTED);
        builder.append("ACTION_ACL_CONNECTED");
        builder.append(" flag:" + Integer.toHexString(intent.getFlags()));
        BluetoothDevice bDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        builder.append(" ").append("EXTRA_DEVICE:").append(bDevice);
    }

    public static void dumpIntent_ACTION_ACL_DISCONNECT_REQUESTED(StringBuilder builder, Intent intent) {
        assertAction(intent, BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
        builder.append("ACTION_ACL_DISCONNECT_REQUESTED");
        builder.append(" flag:" + Integer.toHexString(intent.getFlags()));
        BluetoothDevice bDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        builder.append(" ").append("EXTRA_DEVICE:").append(bDevice);
    }

    public static void dumpIntent_ACTION_ACL_DISCONNECTED(StringBuilder builder, Intent intent) {
        assertAction(intent, BluetoothDevice.ACTION_ACL_DISCONNECTED);
        builder.append("ACTION_ACL_DISCONNECTED");
        builder.append(" flag:" + Integer.toHexString(intent.getFlags()));
        BluetoothDevice bDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        builder.append(" ").append("EXTRA_DEVICE:").append(bDevice);
    }

    public static void dumpIntent_ACTION_BOND_STATE_CHANGED(StringBuilder builder, Intent intent) {
        assertAction(intent, BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        builder.append("ACTION_BOND_STATE_CHANGED");
        builder.append(" flag:" + Integer.toHexString(intent.getFlags()));
        int state = intent.getIntExtra(BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE, BluetoothDevice.BOND_NONE);
        builder.append(" ").append("EXTRA_PREVIOUS_BOND_STATE:").append(toBluetoothDeviceBondStateString(state));
        state = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothAdapter.STATE_OFF);
        builder.append(" ").append("EXTRA_BOND_STATE:").append(toBluetoothDeviceBondStateString(state));
    }

    public static void dumpIntent_ACTION_CONNECTION_STATE_CHANGED(StringBuilder builder, Intent intent) {
        assertAction(intent, BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
        builder.append("ACTION_CONNECTION_STATE_CHANGED");
        builder.append(" flag:" + Integer.toHexString(intent.getFlags()));
        int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.STATE_DISCONNECTED);
        builder.append(" ").append("EXTRA_CONNECTION_STATE:").append(toBluetoothAdapterConnectionStateString(state));
        state = intent.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_STATE, BluetoothAdapter.STATE_DISCONNECTED);
        builder.append(" ").append("EXTRA_PREVIOUS_STATE:").append(toBluetoothAdapterConnectionStateString(state));
    }

    public static void dumpIntent_ACTION_STATE_CHANGED(StringBuilder builder, Intent intent) {
        assertAction(intent, BluetoothAdapter.ACTION_STATE_CHANGED);
        builder.append("ACTION_STATE_CHANGED");
        builder.append(" flag:" + Integer.toHexString(intent.getFlags()));
        int state = intent.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_STATE, BluetoothAdapter.STATE_OFF);
        builder.append(" ").append("EXTRA_PREVIOUS_STATE:").append(toBluetoothAdapterStateString(state));
        state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.STATE_OFF);
        builder.append(" ").append("EXTRA_STATE:").append(toBluetoothAdapterStateString(state));
    }

    private static void assertAction(Intent intent, String action){
        if (!action.equals(intent.getAction())){
            throw new IllegalArgumentException("expected action:" + action + " actural:" + intent.getAction());
        }
    }

    /**
     * @param builder
     * @param indent
     * @param intent
     */
    public static final void dump(StringBuilder builder, int indent, Intent intent) {
        appendln(builder, makeIndent(indent)     + "intent:" + intent);
        appendln(builder, makeIndent(indent + 1) + "action:" + intent.getAction());
        appendln(builder, makeIndent(indent + 1) + "type  :" + intent.getType());

        Bundle extras = intent.getExtras();
        if (null == extras) {
            appendln(builder, makeIndent(indent + 1) + "no extra:");
            return;
        }
        appendln(builder, makeIndent(indent + 1) + "extra:");
        for (String k : extras.keySet()) {
            appendln(builder, makeIndent(indent + 2) + k + ":" + extras.get(k));
        }
    }

    private static final void appendln(StringBuilder builder, String str) {
        builder.append(str);
        builder.append("\n");
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static final BluetoothGattCharacteristic find(BluetoothGatt gatt, UUID serviceUuid, UUID characterUuid) {
        BluetoothGattService service = gatt.getService(serviceUuid);
        if (service == null ){
            return null;
        }

        return service.getCharacteristic(characterUuid);
    }
}
