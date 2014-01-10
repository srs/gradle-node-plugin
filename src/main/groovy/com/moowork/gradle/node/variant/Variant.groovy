package com.moowork.gradle.node.variant

// TODO: Variant needs to be evaluated every time!
final class Variant
{
    def boolean windows

    def File workDir

    def File nodeDir

    def File npmDir

    def File nodeBinDir

    def String nodeExec

    def String npmScriptFile

    def String tarGzDependency

    def String exeDependency
}
