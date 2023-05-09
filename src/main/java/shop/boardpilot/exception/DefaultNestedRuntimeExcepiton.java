package shop.boardpilot.exception;

import org.springframework.core.NestedExceptionUtils;
import org.springframework.core.NestedRuntimeException;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

abstract public class DefaultNestedRuntimeExcepiton extends NestedRuntimeException {
    private final String code;

    @Nullable
    private final String reason;

    public DefaultNestedRuntimeExcepiton(String code, @Nullable String reason) {
        this(code, reason, null);
    }

    public DefaultNestedRuntimeExcepiton(String code, @Nullable String reason, @Nullable Throwable cause) {
        super(null, cause);
        Assert.notNull(code, "Error code is required");
        this.code = code;
        this.reason = reason;
    }

    public String getCode() {
        return this.code;
    }

    public String getReason() {
        return this.reason;
    }
    @Override
    public String getMessage() {
        String msg = this.code + (this.reason != null ? " ," + this.reason : "");
        return NestedExceptionUtils.buildMessage(msg, getCause());
    }
}
