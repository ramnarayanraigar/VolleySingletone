package raigar.ramnarayan.volleysingletone;

/**
 * Created by kriscent on 27/4/18.
 */

public interface SomeCustomListener<T> {
    void getResult(T object, String errorDes, boolean isError);
}
