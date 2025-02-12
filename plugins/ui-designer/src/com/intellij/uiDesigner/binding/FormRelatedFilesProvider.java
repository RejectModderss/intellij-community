// Copyright 2000-2023 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package com.intellij.uiDesigner.binding;

import com.intellij.navigation.GotoRelatedItem;
import com.intellij.navigation.GotoRelatedProvider;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.uiDesigner.GuiFormFileType;
import com.intellij.uiDesigner.UIDesignerBundle;
import com.intellij.uiDesigner.compiler.Utils;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * @author Dmitry Avdeev
 */
public class FormRelatedFilesProvider extends GotoRelatedProvider {

  @Override
  public @NotNull List<? extends GotoRelatedItem> getItems(@NotNull PsiElement context) {
    PsiClass psiClass = PsiTreeUtil.getParentOfType(context, PsiClass.class, false);
    if (psiClass != null) {
      while (psiClass != null) {
        List<PsiFile> forms = FormClassIndex.findFormsBoundToClass(psiClass.getProject(), psiClass);
        if (!forms.isEmpty()) {
          return GotoRelatedItem.createItems(forms, UIDesignerBundle.message("separator.ui.forms"));
        }
        psiClass = PsiTreeUtil.getParentOfType(psiClass, PsiClass.class);
      }
    }
    else {
      PsiFile file = context.getContainingFile();
      if (file != null && file.getFileType() == GuiFormFileType.INSTANCE) {
        try {
          String className = Utils.getBoundClassName(file.getText());
          if (className != null) {
            Project project = file.getProject();
            PsiClass aClass = JavaPsiFacade.getInstance(project).findClass(className, GlobalSearchScope.allScope(project));
            if (aClass != null) {
              return Collections.singletonList(new GotoRelatedItem(aClass, UIDesignerBundle.message("separator.java")));
            }
          }
        }
        catch (Exception ignore) {

        }
      }
    }
    return Collections.emptyList();
  }
}
