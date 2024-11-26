package eu.openanalytics.phaedra.plateservice.util;

import org.apache.commons.lang3.StringUtils;

public class StatusUtils {

  public static final <T extends Enum<T>> T getStatus(String status, Class<T> enumClass, T defaultStatus) {
    return StringUtils.isNotBlank(status) ? Enum.valueOf(enumClass, status) : defaultStatus;
  }

}
