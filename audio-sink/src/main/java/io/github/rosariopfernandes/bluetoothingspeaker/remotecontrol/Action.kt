package io.github.rosariopfernandes.bluetoothingspeaker.remotecontrol

data class Action (
    var type: ActionType = ActionType.PAIR,
    var extras: Map<String, Any>? = null
)