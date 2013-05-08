/*
 * Copyright 2013 Daniel Kurka
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.gwt.site.markdown.toc;

import com.google.gwt.site.markdown.fs.MDNode;
import com.google.gwt.site.markdown.fs.MDParent;

import java.util.List;

public class TocCreator {

  public String createTocForNode(MDParent root, MDNode node) {

    StringBuffer buffer = new StringBuffer();
    buffer.append("<ul>");
    render(root, buffer, node);
    buffer.append("</ul>");

    return buffer.toString();
  }

  private void render(MDNode node, StringBuffer buffer, MDNode tocNode) {
    
    if(node.isExcludeFromToc())
      return;

    if (node.isFolder()) {
      MDParent mdParent = node.asFolder();

      if (node.getDepth() != 0) {
        buffer.append("<li class='folder'>");

        if (mdParent.getHref() != null) {
          StringBuffer url = new StringBuffer();
          if (tocNode.getDepth() > 0) {
            for (int i = 1; i < tocNode.getDepth(); i++) {
              url.append("../");
            }
          }
          url.append(node.getRelativePath());

          buffer.append("<a href='" + url.toString() + mdParent.getHref() + "'>");
        }
        buffer.append(node.getDisplayName());
        if (mdParent.getHref() != null) {
          buffer.append("</a>");
        }
        buffer.append("<ul>");

      }

      List<MDNode> children = mdParent.getChildren();
      for (MDNode child : children) {
        render(child, buffer, tocNode);
      }

      if (node.getDepth() != 0) {

        buffer.append("</li>");
        buffer.append("</ul>");

      }

    } else {

      StringBuffer url = new StringBuffer();
      if (tocNode.getDepth() > 0) {
        for (int i = 1; i < tocNode.getDepth(); i++) {
          url.append("../");
        }
      }

      url.append(node.getRelativePath());

      buffer.append("<li class='file'>");
      // TODO escape HTML
      buffer.append("<a href='" + url.toString() + "'>" + node.getDisplayName() + "</a>");
      buffer.append("</li>");
    }

  }

}
