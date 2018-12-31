#NoEnv  ; Recommended for performance and compatibility with future AutoHotkey releases.
; #Warn  ; Enable warnings to assist with detecting common errors.
SendMode Input  ; Recommended for new scripts due to its superior speed and reliability.
SetWorkingDir %A_ScriptDir%  ; Ensures a consistent starting directory.
SetTitleMatchMode, 2 ; This let's any window that partially matches the given name get activated

NumpadAdd::SoundSet,+3
NumpadSub::SoundSet,-3

#IfWinActive, Snes9X
$ESC::
    Send !{f4}

#IfWinActive, FCEUX
$ESC::
    Send !{f4}

#IfWinActive, Fusion
$ESC::
    Send !{f4}