package test_core.extentions;

import test_core.utils.CleanUtils;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class HardCleanExtention implements AfterAllCallback {

    @Override
    public void afterAll(ExtensionContext extensionContext) {
        CleanUtils.hardClean();
    }
}