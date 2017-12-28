package com.blankj.utilcode.util;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2017/12/14
 *     desc  :
 * </pre>
 */
class TagMessage implements Comparable<TagMessage> {

    Object event;

    String tag;

    int priority;

    TagMessage(Object event, String tag) {
        this(event, tag, 0);
    }

    TagMessage(Object event, String tag, int priority) {
        this.event = event;
        this.tag = tag;
        this.priority = priority;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof TagMessage) {
            TagMessage stickyEvent = (TagMessage) obj;
            // priority should ignore
            return ObjectUtils.equals(stickyEvent.event, event)
                    && ObjectUtils.equals(stickyEvent.tag, tag);
        }
        return false;
    }

    boolean isSameType(final Class eventType, final String tag) {
        return ObjectUtils.equals(event.getClass(), eventType)
                && ObjectUtils.equals(this.tag, tag);
    }

    @Override
    public int compareTo(TagMessage o) {
        return o.priority - priority;
    }

    @Override
    public String toString() {
        return event.getClass() + ", " + tag + ", " + priority;
    }
}
