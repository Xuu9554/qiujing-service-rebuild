package com.qiujing.exception;

import com.qiujing.common.ResponseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.TransactionException;
import org.springframework.validation.BindException;

import java.sql.SQLException;
import java.util.function.Function;

import static com.qiujing.common.Constant.*;

@Getter
@AllArgsConstructor
public enum ExceptionAmityHintEnum {

    /**
     * 登录态失效
     */
    LOGIN_EXPIRED_EXCEPTION_HINT(LoginInvalidException.class, Throwable::getMessage, LOGIN_EXPIRED, false),

    /**
     * 使用{@link javax.validation.constraints}包下的注解时，自定义的message信息将优先于业务异常抛出
     * <p>
     * <b>注意：</b> 如果想要使 {@link javax.validation.constraints} 包下的注解的校验生效，
     * 必须在 controller 的入参前面加上 {@link javax.validation.Valid}。
     * </p>
     */
    BIND_EXCEPTION(BindException.class, t -> ((BindException) t).getAllErrors().get(0).getDefaultMessage(), false),

    /**
     * 项目自定义异常抛出
     *
     * @see ServiceException
     * @see ServiceAssert#isTrue(boolean, String, Object...)
     */
    SERVICE_NORMAL_EXCEPTION_HINT(ServiceException.class, Throwable::getMessage, false),

    CLIENT_ABORT_EXCEPTION_HINT(ClientAbortException.class, t -> "服务器连接被中断，请稍后重试", false),

    CANNOT_CREATE_TRANSACTION_EXCEPTION_HINT(CannotCreateTransactionException.class, t -> "数据库无法连接，请稍后重试"),

    TRANSACTION_EXCEPTION_HINT(TransactionException.class, t -> "数据库异常，请稍后重试"),

    BAD_SQL_GRAMMAR_EXCEPTION_HINT(BadSqlGrammarException.class, t -> "服务器sql执行异常，请稍后重试"),

    SQL_EXCEPTION_HINT(SQLException.class, t -> "服务器sql异常，请稍后重试"),

    RUNTIME_EXCEPTION_HINT(RuntimeException.class, t -> "服务器运行异常，请稍后重试"),

    /**
     * 默认异常，将提示[服务器异常，请稍后重试]
     */
    EXCEPTION_HINT(Exception.class, t -> DEFAULT_ERROR_MESSAGE),

    ;

    /**
     * 异常类型
     */
    private final Class<?> rootCause;

    /**
     * 执行函数
     */
    private final Function<Throwable, String> function;

    /**
     * 接口执行结果
     *
     * @see ResponseEntity#getResult()
     */
    private final Integer result;

    /**
     * 是否需要打印异常日志
     */
    private final boolean needPrintStackTraceOperate;

    /**
     * 构造ExceptionAmityHintEnum
     *
     * @param rootCause 异常类型
     * @param function  执行函数
     */
    ExceptionAmityHintEnum(Class<?> rootCause, Function<Throwable, String> function) {
        this(rootCause, function, true);
    }

    /**
     * 构造ExceptionAmityHintEnum
     *
     * @param rootCause                  异常类型
     * @param function                   执行函数
     * @param needPrintStackTraceOperate 是否需要打印异常日志
     */
    ExceptionAmityHintEnum(Class<?> rootCause, Function<Throwable, String> function, boolean needPrintStackTraceOperate) {
        this(rootCause, function, FAIL, needPrintStackTraceOperate);
    }

}
