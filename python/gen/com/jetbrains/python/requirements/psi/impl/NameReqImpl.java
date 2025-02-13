// This is a generated file. Not intended for manual editing.
package com.jetbrains.python.requirements.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.jetbrains.python.requirements.psi.RequirementsTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.jetbrains.python.requirements.psi.*;

public class NameReqImpl extends ASTWrapperPsiElement implements NameReq {

  public NameReqImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull Visitor visitor) {
    visitor.visitNameReq(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof Visitor) accept((Visitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public Extras getExtras() {
    return findChildByClass(Extras.class);
  }

  @Override
  @NotNull
  public List<HashOption> getHashOptionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, HashOption.class);
  }

  @Override
  @Nullable
  public QuotedMarker getQuotedMarker() {
    return findChildByClass(QuotedMarker.class);
  }

  @Override
  @NotNull
  public SimpleName getSimpleName() {
    return findNotNullChildByClass(SimpleName.class);
  }

  @Override
  @Nullable
  public Versionspec getVersionspec() {
    return findChildByClass(Versionspec.class);
  }

}
