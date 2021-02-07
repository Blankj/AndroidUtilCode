package com.blankj.utilcode.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;

/*
            (   (                ) (             (     (
            )\ ))\ )    *   ) ( /( )\ )     (    )\ )  )\ )
 (   (   ( (()/(()/(  ` )  /( )\()|()/((    )\  (()/( (()/(
 )\  )\  )\ /(_))(_))  ( )(_)|(_)\ /(_))\((((_)( /(_)) /(_))
((_)((_)((_|_))(_))   (_(_()) _((_|_))((_))\ _ )(_))_ (_))
| __\ \ / /|_ _| |    |_   _|| || | _ \ __(_)_\(_)   \/ __|
| _| \ V /  | || |__    | |  | __ |   / _| / _ \ | |) \__ \
|___| \_/  |___|____|   |_|  |_||_|_|_\___/_/ \_\|___/|___/
....................../´¯/)
....................,/¯../
.................../..../
............./´¯/'...'/´¯¯`·¸
........../'/.../..../......./¨¯\
........('(...´...´.... ¯~/'...')
.........\.................'...../
..........''...\.......... _.·´
............\..............(
..............\.............\...
*/

/**
 * @author Chris Basinger
 * @email evilthreads669966@gmail.com
 * @date 02/06/20
 *
 * provides Android component helper methods for enabling, disabling, and checking whether enabled or disabled.
 **/
public final class AndroidComponentUtils {

    /**
     * Checks whether a component is enabled. Whether it can be instantiated.
     *
     * @param ctx android application context
     * @param component the component's class. example: MyComponent.class
     * @return returns true if the component is enabled
     */
    public static Boolean isComponentEnabled(Context ctx, Class component){
       final ComponentName componentName = new ComponentName(ctx, component);
        final int state = ctx.getPackageManager().getComponentEnabledSetting(componentName);
        return state == PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
    }

    /**
     * Checks whether a component is disabled. Whether it can be instantiated.
     *
     * @param ctx android application context
     * @param component the component's class. example: MyComponent.class
     * @return returns true if the component is disabled
     */
    public static Boolean isComponentDisabled(Context ctx, Class component){
        final ComponentName componentName = new ComponentName(ctx, component);
        final int state = ctx.getPackageManager().getComponentEnabledSetting(componentName);
        return state != PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
    }

    /**
     * Enables an Android component for use if it is not already enabled
     *
     * @param ctx android application context
     * @param component the component's class. example: MyComponent.class
     * @return returns true if the component wasn't already enabled
     */
    public static Boolean enableComponent(Context ctx, Class component){
        final ComponentName componentName = new ComponentName(ctx, component);
        final int state = ctx.getPackageManager().getComponentEnabledSetting(componentName);
        if(state != PackageManager.COMPONENT_ENABLED_STATE_ENABLED){
            ctx.getPackageManager().setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
            return true;
        }
        return false;
    }

    /**
     * Disables an Android component for use if it is not already disabled
     *
     * @param ctx android application context
     * @param component the component's class. example: MyComponent.class
     * @return returns true if the component wasn't already disabled.
     */
    public static Boolean disableComponent(Context ctx, Class component){
        final ComponentName componentName = new ComponentName(ctx, component);
        final int state = ctx.getPackageManager().getComponentEnabledSetting(componentName);
        if(state != PackageManager.COMPONENT_ENABLED_STATE_DISABLED){
            ctx.getPackageManager().setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
            return true;
        }
        return false;
    }
}
