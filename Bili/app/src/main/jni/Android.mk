LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
LOCAL_LDLIBS    := -lm -llog
LOCAL_SRC_FILES := com_jack_zhou_bili_util_JNIClass.c
LOCAL_MODULE := myjni
include $(BUILD_SHARED_LIBRARY)