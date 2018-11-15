package org.xavier.hyggecache.activate;

import org.springframework.context.annotation.*;
import org.xavier.hyggecache.annotation.EnableHyggeCache;

import java.util.ArrayList;

/**
 * 描述信息：<br/>
 * AopGetCacheHelper 实现模式选择器
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.14
 * @since Jdk 1.8
 */
public class AdviceModeConfigurationSelector extends AdviceModeImportSelector<EnableHyggeCache> {

    @Override
    public String[] selectImports(AdviceMode adviceMode) {
        switch (adviceMode) {
            case PROXY:
                return getProxyImports();
            default:
                throw new RuntimeException("Unsupported AdviceMode: " + adviceMode.name());
        }
    }

    private String[] getProxyImports() {
        ArrayList<String> temp = new ArrayList();
        temp.add(AutoProxyRegistrar.class.getName());
        String[] result = temp.toArray(new String[temp.size()]);
        return result;
    }
}
