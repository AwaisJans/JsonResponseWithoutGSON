package com.json.response.without.gson.app.utils.gson.internal;

public class TroubleshootingGuide {
  private TroubleshootingGuide() {}

  /** Creates a URL referring to the specified troubleshooting section. */
  public static String createUrl(String id) {
    return "https://github.com/google/gson/blob/main/Troubleshooting.md#" + id;
  }
}
