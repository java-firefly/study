package pdf;

import com.itextpdf.text.Element;

public class PdfCustomCell {
	private String text;
	private int colSpan=1;
	private int roleSpan=1;
	private int align = Element.ALIGN_CENTER;
	public PdfCustomCell(String text) {
		setText(text);
	}
	public PdfCustomCell(int align, String text) {
		setAlign(align);
		setText(text);
	}
	public PdfCustomCell(int align, String text, int colSpan) {
		setAlign(align);
		setText(text);
		setColSpan(colSpan);
	}
	public PdfCustomCell(String text, int colSpan) {
		setText(text);
		setColSpan(colSpan);
	}
	public PdfCustomCell(String text, int colSpan, int roleSpan) {
		setText(text);
		setColSpan(colSpan);
		setRoleSpan(roleSpan);
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getColSpan() {
		return colSpan;
	}
	public void setColSpan(int colSpan) {
		this.colSpan = colSpan;
	}
	public int getRoleSpan() {
		return roleSpan;
	}
	public void setRoleSpan(int roleSpan) {
		this.roleSpan = roleSpan;
	}
	public int getAlign() {
		return align;
	}
	public void setAlign(int align) {
		this.align = align;
	}
}
