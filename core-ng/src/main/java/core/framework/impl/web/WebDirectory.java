package core.framework.impl.web;

import core.framework.api.util.Exceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author neo
 */
public class WebDirectory {
    private final Logger logger = LoggerFactory.getLogger(WebDirectory.class);

    private final Path webPath;
    public boolean localEnv;

    public WebDirectory() {
        this.webPath = locateWebPath();
    }

    private Path locateWebPath() {
        String webPathValue = System.getProperty("core.web");
        if (webPathValue != null) {
            Path webPath = Paths.get(webPathValue).toAbsolutePath();
            if (Files.exists(webPath) && Files.isDirectory(webPath)) {
                logger.info("found -Dcore.web, use it as web directory, path={}", webPath);
                return webPath;
            }
        } else {
            Path webPath = Paths.get("./src/main/dist/web").toAbsolutePath();
            if (Files.exists(webPath) && Files.isDirectory(webPath)) {
                logger.warn("found local web directory, this should only happen in local dev env or test, path={}", webPath);
                localEnv = true;
                return webPath;
            }
        }
        logger.info("can not locate web directory");
        return null;
    }

    public Path path(String path) {
        if (webPath == null)
            throw new Error("web path does not exist, check -Dcore.web or set working dir to be module path for local dev env.");
        if (path.charAt(0) != '/') throw Exceptions.error("path must start with '/', path={}", path);
        return webPath.resolve(path.substring(1)).toAbsolutePath();
    }
}
