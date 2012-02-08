package ar.com.gm.notij.view;

import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JOptionPane;

import ar.com.gm.notij.notifier.NotificationObservable;
import ar.com.gm.notij.notifier.NotificationObservable.Notification;
import ar.com.gm.notij.notifier.NotificationObserver;
import ar.com.gm.notij.notifier.Notifier;

/**
 * {@link NotificationObserver} that acts on notification showing a
 * {@link JOptionPane} message dialog.
 * 
 * @author gmatheu
 * 
 */
public class DialogNotificationHandler implements NotificationObserver {

	/**
	 * Dialog parent component
	 */
	private Component parent;
	/**
	 * Dialog titles
	 */
	private String dialogTitle;

	/**
	 * Default constructor
	 * 
	 * @param parent
	 *            parent
	 * @param dialogTitle
	 *            dialog title
	 */
	public DialogNotificationHandler(Component parent, String dialogTitle) {
		this.parent = parent;
		this.dialogTitle = dialogTitle;
	}

	/**
	 * Shows a {@link JOptionPane} dialog on each notification with
	 * {@link #parent} as parent, {@link Notification#getMessage()} as message,
	 * {@link #dialogTitle} as title and messageType based on
	 * {@link Notification.Level} (ERROR=ERROR_MESSAGE,
	 * INFO=INFORMATION_MESSAGE, WARN=WARNING_MESSAGE)
	 * 
	 * @see JOptionPane#showMessageDialog(Component, Object, String, int)
	 */
	public void update(NotificationObservable subject, Notification notification) {
		if (!notification.isConsumed()) {
			int messageType = 0;
			switch (notification.getLevel()) {
			case ERROR:
				messageType = JOptionPane.ERROR_MESSAGE;
				break;
			case INFO:
				messageType = JOptionPane.INFORMATION_MESSAGE;
				break;
			case WARN:
				messageType = JOptionPane.WARNING_MESSAGE;
				break;
			}

			if (!GraphicsEnvironment.isHeadless()) {
				JOptionPane.showMessageDialog(parent, notification.getMessage(), dialogTitle, messageType);
				notification.setConsumed();
			}
		}
	}

	/**
	 * Convenience method to add DialogNotificationHandler behavior to a
	 * JDialog. I.e: Create a DialogNotificationHandler for window and with
	 * title; add it to Notifier observers; and adding a {@link WindowListener}
	 * to window to remove the DialogNotificationHandler on window closing
	 * 
	 * @see WindowListener
	 * @see Notifier
	 * @param window
	 *            to add the behavior
	 * @param title
	 *            title to be used by DialogNotificationHandler instance
	 * @return used DialogNotificationHandler instance
	 */
	public static DialogNotificationHandler addDialogNotificationsHandling(Window window, String title) {
		final DialogNotificationHandler no = new DialogNotificationHandler(window, title);
		Notifier.getInstance().addObserver(no);
		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				Notifier.getInstance().deleteObserver(no);
			}
		});

		return no;
	}
}
