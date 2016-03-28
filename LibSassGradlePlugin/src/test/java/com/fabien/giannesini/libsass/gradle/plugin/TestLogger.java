package com.fabien.giannesini.libsass.gradle.plugin;

import org.gradle.api.logging.LogLevel;
import org.gradle.api.logging.Logger;
import org.slf4j.Marker;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestLogger implements Logger {

    @Override
    public void debug(final String arg0) {
        log.debug(arg0);
    }

    @Override
    public void debug(final String arg0, final Object arg1) {
        log.debug(arg0, arg1);
    }

    @Override
    public void debug(final String arg0, final Throwable arg1) {
        log.debug(arg0, arg1);
    }

    @Override
    public void debug(final Marker arg0, final String arg1) {
        log.debug(arg0, arg1);
    }

    @Override
    public void debug(final String arg0, final Object arg1, final Object arg2) {
        log.debug(arg0, arg1, arg2);
    }

    @Override
    public void debug(final Marker arg0, final String arg1, final Object arg2) {
        log.debug(arg0, arg1, arg2);
    }

    @Override
    public void debug(final Marker arg0, final String arg1,
            final Object... arg2) {
        log.debug(arg0, arg1, arg2);
    }

    @Override
    public void debug(final Marker arg0, final String arg1,
            final Throwable arg2) {
        log.debug(arg0, arg1, arg2);
    }

    @Override
    public void debug(final Marker arg0, final String arg1, final Object arg2,
            final Object arg3) {
        log.debug(arg0, arg1, arg2, arg3);
    }

    @Override
    public void error(final String arg0) {
        log.error(arg0);
    }

    @Override
    public void error(final String arg0, final Object arg1) {
        log.error(arg0, arg1);
    }

    @Override
    public void error(final String arg0, final Object... arg1) {
        log.error(arg0, arg1);
    }

    @Override
    public void error(final String arg0, final Throwable arg1) {
        log.error(arg0, arg1);
    }

    @Override
    public void error(final Marker arg0, final String arg1) {
        log.error(arg0, arg1);
    }

    @Override
    public void error(final String arg0, final Object arg1, final Object arg2) {
        log.error(arg0, arg1, arg2);
    }

    @Override
    public void error(final Marker arg0, final String arg1, final Object arg2) {
        log.error(arg0, arg1, arg2);
    }

    @Override
    public void error(final Marker arg0, final String arg1,
            final Object... arg2) {
        log.error(arg0, arg1, arg2);
    }

    @Override
    public void error(final Marker arg0, final String arg1,
            final Throwable arg2) {
        log.error(arg0, arg1, arg2);
    }

    @Override
    public void error(final Marker arg0, final String arg1, final Object arg2,
            final Object arg3) {
        log.error(arg0, arg1, arg2, arg3);
    }

    @Override
    public String getName() {
        return log.getName();
    }

    @Override
    public void info(final String arg0) {
        log.info(arg0);
    }

    @Override
    public void info(final String arg0, final Object arg1) {
        log.info(arg0, arg1);
    }

    @Override
    public void info(final String arg0, final Throwable arg1) {
        log.info(arg0, arg1);
    }

    @Override
    public void info(final Marker arg0, final String arg1) {
        log.info(arg0, arg1);
    }

    @Override
    public void info(final String arg0, final Object arg1, final Object arg2) {
        log.info(arg0, arg1, arg2);
    }

    @Override
    public void info(final Marker arg0, final String arg1, final Object arg2) {
        log.info(arg0, arg1, arg2);
    }

    @Override
    public void info(final Marker arg0, final String arg1,
            final Object... arg2) {
        log.info(arg0, arg1, arg2);
    }

    @Override
    public void info(final Marker arg0, final String arg1,
            final Throwable arg2) {
        log.info(arg0, arg1, arg2);
    }

    @Override
    public void info(final Marker arg0, final String arg1, final Object arg2,
            final Object arg3) {
        log.info(arg0, arg1, arg2, arg3);
    }

    @Override
    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    @Override
    public boolean isDebugEnabled(final Marker arg0) {
        return log.isDebugEnabled(arg0);
    }

    @Override
    public boolean isErrorEnabled() {
        return log.isErrorEnabled();
    }

    @Override
    public boolean isErrorEnabled(final Marker arg0) {
        return log.isErrorEnabled(arg0);
    }

    @Override
    public boolean isInfoEnabled() {
        return log.isInfoEnabled();
    }

    @Override
    public boolean isInfoEnabled(final Marker arg0) {
        return log.isInfoEnabled(arg0);
    }

    @Override
    public boolean isTraceEnabled() {
        return log.isTraceEnabled();
    }

    @Override
    public boolean isTraceEnabled(final Marker arg0) {
        return log.isTraceEnabled(arg0);
    }

    @Override
    public boolean isWarnEnabled() {
        return log.isWarnEnabled();
    }

    @Override
    public boolean isWarnEnabled(final Marker arg0) {
        return log.isWarnEnabled(arg0);
    }

    @Override
    public void trace(final String arg0) {
        log.trace(arg0);
    }

    @Override
    public void trace(final String arg0, final Object arg1) {
        log.trace(arg0, arg1);
    }

    @Override
    public void trace(final String arg0, final Object... arg1) {
        log.trace(arg0, arg1);
    }

    @Override
    public void trace(final String arg0, final Throwable arg1) {
        log.trace(arg0, arg1);
    }

    @Override
    public void trace(final Marker arg0, final String arg1) {
        log.trace(arg0, arg1);
    }

    @Override
    public void trace(final String arg0, final Object arg1, final Object arg2) {
        log.trace(arg0, arg1, arg2);
    }

    @Override
    public void trace(final Marker arg0, final String arg1, final Object arg2) {
        log.trace(arg0, arg1, arg2);
    }

    @Override
    public void trace(final Marker arg0, final String arg1,
            final Object... arg2) {
        log.trace(arg0, arg1, arg2);
    }

    @Override
    public void trace(final Marker arg0, final String arg1,
            final Throwable arg2) {
        log.trace(arg0, arg1, arg2);
    }

    @Override
    public void trace(final Marker arg0, final String arg1, final Object arg2,
            final Object arg3) {
        log.trace(arg0, arg1, arg2, arg3);
    }

    @Override
    public void warn(final String arg0) {
        log.warn(arg0);
    }

    @Override
    public void warn(final String arg0, final Object arg1) {
        log.warn(arg0, arg1);
    }

    @Override
    public void warn(final String arg0, final Object... arg1) {
        log.warn(arg0, arg1);
    }

    @Override
    public void warn(final String arg0, final Throwable arg1) {
        log.warn(arg0, arg1);
    }

    @Override
    public void warn(final Marker arg0, final String arg1) {
        log.warn(arg0, arg1);
    }

    @Override
    public void warn(final String arg0, final Object arg1, final Object arg2) {
        log.warn(arg0, arg1, arg2);
    }

    @Override
    public void warn(final Marker arg0, final String arg1, final Object arg2) {
        log.warn(arg0, arg1, arg2);
    }

    @Override
    public void warn(final Marker arg0, final String arg1,
            final Object... arg2) {
        log.warn(arg0, arg1, arg2);
    }

    @Override
    public void warn(final Marker arg0, final String arg1,
            final Throwable arg2) {
        log.warn(arg0, arg1, arg2);
    }

    @Override
    public void warn(final Marker arg0, final String arg1, final Object arg2,
            final Object arg3) {
        log.warn(arg0, arg1, arg2, arg3);
    }

    @Override
    public void debug(final String arg0, final Object... arg1) {
        log.debug(arg0, arg1);
    }

    @Override
    public void info(final String arg0, final Object... arg1) {
        log.info(arg0, arg1);
    }

    @Override
    public boolean isEnabled(final LogLevel arg0) {
        return false;
    }

    @Override
    public boolean isLifecycleEnabled() {
        return false;
    }

    @Override
    public boolean isQuietEnabled() {
        return false;
    }

    @Override
    public void lifecycle(final String arg0) {

    }

    @Override
    public void lifecycle(final String arg0, final Object... arg1) {

    }

    @Override
    public void lifecycle(final String arg0, final Throwable arg1) {

    }

    @Override
    public void log(final LogLevel arg0, final String arg1) {

    }

    @Override
    public void log(final LogLevel arg0, final String arg1,
            final Object... arg2) {

    }

    @Override
    public void log(final LogLevel arg0, final String arg1,
            final Throwable arg2) {
    }

    @Override
    public void quiet(final String arg0) {

    }

    @Override
    public void quiet(final String arg0, final Object... arg1) {

    }

    @Override
    public void quiet(final String arg0, final Throwable arg1) {

    }

}
