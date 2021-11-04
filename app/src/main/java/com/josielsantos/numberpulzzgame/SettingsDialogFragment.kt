package com.josielsantos.numberpulzzgame

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class SettingsDialogFragment(
    /** O tamanho.  */
    var size: Int
) : DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        // Set the dialog title
        builder.setTitle("Defina o tamanho do quebra-cabeça")
            .setSingleChoiceItems(
                R.array.size_options, size - 2
            ) { dialog, which -> size = which + 2 }
            .setPositiveButton(
                "Confirmar"
            ) { dialog, id ->
                (getActivity() as MainActivity)
                    .changeSize(size)
            }
            .setNegativeButton(
                "Cancelar"
            ) { dialog, id -> dialog.cancel() }
        val settingsDialog =  builder.create()
        settingsDialog.show()
        return  settingsDialog
    }
}