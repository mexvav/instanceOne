package core.jpa.interfaces;

public interface HasLength {
    int DEFAULT = 0;
    int getLength();

    void setLength(int length);
}
