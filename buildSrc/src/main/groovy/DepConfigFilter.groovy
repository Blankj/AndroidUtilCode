/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/07/13
 *     desc  :
 * </pre>
 */
@FunctionalInterface
interface DepConfigFilter {
    boolean accept(String name, DepConfig config);
}
