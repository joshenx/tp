package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;

/**
 * Archives an appointment identified using it's displayed index from the appointment book.
 */
public class ArchiveAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "archive";

    public static final String MESSAGE_USAGE = "appt " + COMMAND_WORD
            + ": Archives the appointment identified by the index number used in the displayed appointment list.\n"
            + "Parameters: INDEX (must be a positive integer)\n" + "Example: appt " + COMMAND_WORD + " 1";

    public static final String MESSAGE_ARCHIVE_APPOINTMENT_SUCCESS = "Archived Appointment: %1$s";

    private final Index targetIndex;

    public ArchiveAppointmentCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Appointment> lastShownList = model.getFilteredAppointmentList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
        }

        Appointment appointmentToArchive = lastShownList.get(targetIndex.getZeroBased());
        model.archiveAppointment(appointmentToArchive);
        return new CommandResult(String.format(MESSAGE_ARCHIVE_APPOINTMENT_SUCCESS, appointmentToArchive));
    }

    @Override public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ArchiveAppointmentCommand // instanceof handles nulls
                && targetIndex.equals(((ArchiveAppointmentCommand) other).targetIndex)); // state check
    }
}
