// Copyright 2000-2024 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license.
package org.jetbrains.plugins.terminal.block

import com.intellij.openapi.Disposable
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.editor.ex.DocumentEx
import com.intellij.openapi.editor.ex.EditorEx
import com.intellij.openapi.editor.impl.DocumentImpl
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.intellij.terminal.JBTerminalSystemSettingsProviderBase
import com.intellij.ui.ExperimentalUI
import org.jetbrains.plugins.terminal.exp.*

class TestTerminalOutputManager(project: Project, parentDisposable: Disposable) {
  private val editor: EditorEx = createEditor(project, parentDisposable)
  private val outputModel: TerminalOutputModel = TerminalOutputModel(editor)

  init {
    editor.highlighter = TerminalTextHighlighter(outputModel)
  }

  val terminalOutputHighlighter: TerminalTextHighlighter
    get() = editor.highlighter as TerminalTextHighlighter

  val document: DocumentEx
    get() = editor.document

  fun createBlock(command: String, output: TestCommandOutput) {
    val block = outputModel.createBlock(command, null)
    outputModel.putHighlightings(block, output.highlightings)
    editor.document.insertString(0, output.text)
  }

  companion object {
    private fun createEditor(project: Project, parentDisposable: Disposable): EditorEx {
      ExperimentalUI.isNewUI() // `ExperimentalUI` runs `NewUiValue.initialize` in its static init
      val document = DocumentImpl("", true)
      val editor = TerminalUiUtils.createOutputEditor(document, project, JBTerminalSystemSettingsProviderBase())
      Disposer.register(parentDisposable) {
        EditorFactory.getInstance().releaseEditor(editor)
      }
      return editor
    }
  }
}

data class TestCommandOutput(val text: String, val highlightings: List<HighlightingInfo>)
