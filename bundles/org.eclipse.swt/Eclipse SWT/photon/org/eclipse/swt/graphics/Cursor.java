package org.eclipse.swt.graphics;

/*
 * (c) Copyright IBM Corp. 2000, 2001.
 * All Rights Reserved
 */

import org.eclipse.swt.internal.photon.*;
import org.eclipse.swt.*;

/**
 * Instances of this class manage operating system resources that
 * specify the appearance of the on-screen pointer. To create a
 * cursor you specify the device and either a simple cursor style
 * describing one of the standard operating system provided cursors
 * or the image and mask data for the desired appearance.
 * <p>
 * Application code must explicitly invoke the <code>Cursor.dispose()</code> 
 * method to release the operating system resources managed by each instance
 * when those instances are no longer required.
 * </p>
 * <dl>
 * <dt><b>Styles:</b></dt>
 * <dd>
 *   CURSOR_ARROW, CURSOR_WAIT, CURSOR_CROSS, CURSOR_APPSTARTING, CURSOR_HELP,
 *   CURSOR_SIZEALL, CURSOR_SIZENESW, CURSOR_SIZENS, CURSOR_SIZENWSE, CURSOR_SIZEWE,
 *   CURSOR_SIZEN, CURSOR_SIZES, CURSOR_SIZEE, CURSOR_SIZEW, CURSOR_SIZENE, CURSOR_SIZESE,
 *   CURSOR_SIZESW, CURSOR_SIZENW, CURSOR_UPARROW, CURSOR_IBEAM, CURSOR_NO, CURSOR_HAND
 * </dd>
 * </dl>
 * <p>
 * Note: Only one of the above styles may be specified.
 * </p>
 */
public final class Cursor {

	/**
	 * the type to the OS cursor resource
	 * (Warning: This field is platform dependent)
	 */
	 public int type;

	/**
	 * the handle to the OS cursor resource
	 * (Warning: This field is platform dependent)
	 */
	public int bitmap;
	
	/**
	 * the device where this cursor was created
	 */
	Device device;

Cursor() {
}

/**	 
 * Constructs a new cursor given a device and a style
 * constant describing the desired cursor appearance.
 * <p>
 * You must dispose the cursor when it is no longer required. 
 * </p>
 *
 * @param device the device on which to allocate the cursor
 * @param style the style of cursor to allocate
 * 
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_INVALID_ARGUMENT - when an unknown style is specified</li>
 * </ul>
 *
 * @see Cursor for the supported style values
 */
public Cursor(Device device, int style) {
	if (device == null) device = Device.getDevice();
	if (device == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
	this.device = device;
	switch (style) {
		case SWT.CURSOR_ARROW: 		type = OS.Ph_CURSOR_POINTER; break;
		case SWT.CURSOR_WAIT: 		type = OS.Ph_CURSOR_CLOCK; break;
		case SWT.CURSOR_HAND: 		type = OS.Ph_CURSOR_FINGER; break;
		case SWT.CURSOR_CROSS: 		type = OS.Ph_CURSOR_CROSSHAIR; break;
		case SWT.CURSOR_APPSTARTING:	type = OS.Ph_CURSOR_POINT_WAIT; break;
		case SWT.CURSOR_HELP: 		type = OS.Ph_CURSOR_QUESTION_POINT; break;
		case SWT.CURSOR_SIZEALL: 	type = OS.Ph_CURSOR_MOVE; break;
		case SWT.CURSOR_SIZENESW: 	type = OS.Ph_CURSOR_MOVE; break;
		case SWT.CURSOR_SIZENS: 	type = OS.Ph_CURSOR_DRAG_VERTICAL; break;
		case SWT.CURSOR_SIZENWSE:	type = OS.Ph_CURSOR_MOVE; break;
		case SWT.CURSOR_SIZEWE: 	type = OS.Ph_CURSOR_DRAG_HORIZONTAL; break;
		case SWT.CURSOR_SIZEN: 		type = OS.Ph_CURSOR_DRAG_TOP; break;
		case SWT.CURSOR_SIZES: 		type = OS.Ph_CURSOR_DRAG_BOTTOM; break;
		case SWT.CURSOR_SIZEE: 		type = OS.Ph_CURSOR_DRAG_RIGHT; break;
		case SWT.CURSOR_SIZEW: 		type = OS.Ph_CURSOR_DRAG_LEFT; break;
		case SWT.CURSOR_SIZENE: 	type = OS.Ph_CURSOR_DRAG_TR; break;
		case SWT.CURSOR_SIZESE: 	type = OS.Ph_CURSOR_DRAG_BR; break;
		case SWT.CURSOR_SIZESW:		type = OS.Ph_CURSOR_DRAG_BL; break;
		case SWT.CURSOR_SIZENW: 	type = OS.Ph_CURSOR_DRAG_TL; break;
		case SWT.CURSOR_UPARROW: 	type = OS.Ph_CURSOR_FINGER; break;
		case SWT.CURSOR_IBEAM: 		type = OS.Ph_CURSOR_INSERT; break;
		case SWT.CURSOR_NO: 		type = OS.Ph_CURSOR_DONT; break;
		default:
			SWT.error(SWT.ERROR_INVALID_ARGUMENT);
	}
	if (type == 0) SWT.error(SWT.ERROR_NO_HANDLES);
	if (device.tracking) device.new_Object(this);
}

/**	 
 * Constructs a new cursor given a device, image and mask
 * data describing the desired cursor appearance, and the x
 * and y co-ordinates of the <em>hotspot</em> (that is, the point
 * within the area covered by the cursor which is considered
 * to be where the on-screen pointer is "pointing").
 * <p>
 * The mask data is allowed to be null, but in this case the source
 * must be an ImageData representing an icon that specifies both
 * color data and mask data.
 * <p>
 * You must dispose the cursor when it is no longer required. 
 * </p>
 *
 * @param device the device on which to allocate the cursor
 * @param source the color data for the cursor
 * @param mask the mask data for the cursor (or null)
 * @param hotspotX the x coordinate of the cursor's hotspot
 * @param hotspotY the y coordinate of the cursor's hotspot
 *
 * @exception IllegalArgumentException <ul>
 *    <li>ERROR_NULL_ARGUMENT - when a null argument is passed that is not allowed</li>
 *    <li>ERROR_INVALID_ARGUMENT - if the source and the mask are not the same 
 *          size, or either is not of depth one, or if the hotspot is outside 
 *          the bounds of the image</li>
 * </ul>
 * @exception SWTError <ul>
 *    <li>ERROR_NO_HANDLES - if an error occurred constructing the cursor</li>
 * </ul>
 */
public Cursor(Device device, ImageData source, ImageData mask, int hotspotX, int hotspotY) {
	if (device == null) device = Device.getDevice();
	if (device == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
	this.device = device;
	if (source == null) SWT.error(SWT.ERROR_NULL_ARGUMENT);
	if (mask == null) {
		if (source.getTransparencyType() != SWT.TRANSPARENCY_MASK) {
			SWT.error(SWT.ERROR_NULL_ARGUMENT);
		}
		mask = source.getTransparencyMask();
	}
	/* Check the bounds. Mask must be the same size as source */
	if (mask.width != source.width || mask.height != source.height) {
		SWT.error(SWT.ERROR_INVALID_ARGUMENT);
	}
	/* Check color depths */
	if (mask.depth != 1) SWT.error(SWT.ERROR_INVALID_ARGUMENT);
	if (source.depth != 1) SWT.error(SWT.ERROR_INVALID_ARGUMENT);
	/* Check the hotspots */
	if (hotspotX >= source.width || hotspotX < 0 ||
		hotspotY >= source.height || hotspotY < 0) {
		SWT.error(SWT.ERROR_INVALID_ARGUMENT);
	}
	type = OS.Ph_CURSOR_BITMAP;
	
	short w = (short)source.width;
	short h = (short)source.height;
	ImageData mask1 = new ImageData(w, h, 1, source.palette);
	ImageData mask2 = new ImageData(w, h, 1, mask.palette);
	for (int y=0; y<h; y++) {
		for (int x=0; x<w; x++) {
			int mask1_pixel, src_pixel = source.getPixel(x, y);
			int mask2_pixel, mask_pixel = mask.getPixel(x, y);
			if (src_pixel == 0 && mask_pixel == 0) {
				// BLACK
				mask1_pixel = 0;
				mask2_pixel = 1;
			} else if (src_pixel == 0 && mask_pixel == 1) {
				// WHITE - cursor color
				mask1_pixel = 1;
				mask2_pixel = 0;
			} else if (src_pixel == 1 && mask_pixel == 0) {
				// SCREEN
				mask1_pixel = 0;
				mask2_pixel = 0;
			} else {
				/*
				* Feature in Photon. It is not possible to have
				* the reverse screen case using the Photon support.
				* Reverse screen will be the same as screen.
				*/
				// REVERSE SCREEN -> SCREEN
				mask1_pixel = 0;
				mask2_pixel = 0;
			}
			mask1.setPixel(x, y, mask1_pixel);
			mask2.setPixel(x, y, mask2_pixel);
		}
	}
	
	PhCursorDef_t cursor = new PhCursorDef_t();
	cursor.size1_x = w;
	cursor.size1_y = h;
	cursor.offset1_x = (short)-hotspotX;
	cursor.offset1_y = (short)-hotspotY;
	cursor.bytesperline1 = (byte)mask1.bytesPerLine;
	cursor.color1 = OS.Ph_CURSOR_DEFAULT_COLOR;
	cursor.size2_x = w;
	cursor.size2_y = h;
	cursor.offset2_x = (short)-hotspotX;
	cursor.offset2_y = (short)-hotspotY;
	cursor.bytesperline2 = (byte)mask2.bytesPerLine;
	cursor.color2 = 0x000000;
	int mask1Size = cursor.bytesperline1 * cursor.size1_y;
	int mask2Size = cursor.bytesperline2 * cursor.size2_y;	
	bitmap = OS.malloc(PhCursorDef_t.sizeof + mask1Size + mask2Size);
	if (bitmap == 0) SWT.error(SWT.ERROR_NO_HANDLES);
	OS.memmove(bitmap, cursor, PhCursorDef_t.sizeof);
	OS.memmove(bitmap + PhCursorDef_t.sizeof, mask1.data, mask1Size);
	OS.memmove(bitmap + PhCursorDef_t.sizeof + mask1Size, mask2.data, mask2Size);
	if (device.tracking) device.new_Object(this);
}

/**
 * Disposes of the operating system resources associated with
 * the cursor. Applications must dispose of all cursors which
 * they allocate.
 */
public void dispose () {
	if (type == 0) return;
	if (type == OS.Ph_CURSOR_BITMAP && bitmap != 0) {
		OS.free(bitmap);
	}
	type = bitmap = 0;
	if (device.tracking) device.dispose_Object(this);
	device = null;
}

/**
 * Compares the argument to the receiver, and returns true
 * if they represent the <em>same</em> object using a class
 * specific comparison.
 *
 * @param object the object to compare with this object
 * @return <code>true</code> if the object is the same as this object and <code>false</code> otherwise
 *
 * @see #hashCode
 */
public boolean equals (Object object) {
	if (object == this) return true;
	if (!(object instanceof Cursor)) return false;
	Cursor cursor = (Cursor) object;
	return device == cursor.device && type == cursor.type &&
		bitmap == cursor.bitmap;
}

/**
 * Returns an integer hash code for the receiver. Any two 
 * objects which return <code>true</code> when passed to 
 * <code>equals</code> must return the same value for this
 * method.
 *
 * @return the receiver's hash
 *
 * @see #equals
 */
public int hashCode () {
	return bitmap ^ type;
}

/**
 * Returns <code>true</code> if the cursor has been disposed,
 * and <code>false</code> otherwise.
 * <p>
 * This method gets the dispose state for the cursor.
 * When a cursor has been disposed, it is an error to
 * invoke any other method using the cursor.
 *
 * @return <code>true</code> when the cursor is disposed and <code>false</code> otherwise
 */
public boolean isDisposed() {
	return type == 0;
}

public static Cursor photon_new(Device device, int type, int bitmap) {
	if (device == null) device = Device.getDevice();
	Cursor cursor = new Cursor();
	cursor.type = type;
	cursor.bitmap = bitmap;
	cursor.device = device;
	return cursor;
}

/**
 * Returns a string containing a concise, human-readable
 * description of the receiver.
 *
 * @return a string representation of the receiver
 */
public String toString () {
	if (isDisposed()) return "Cursor {*DISPOSED*}";
	return "Cursor {" + type + "," + bitmap + "}";
}

}
