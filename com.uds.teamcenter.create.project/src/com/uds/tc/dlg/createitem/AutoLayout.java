/**************************************************************************************************                                      
 *                                               ��Ȩ��UDS���У�2016
 **************************************************************************************************                             
 *  
 *        Function Description
 *        
 **************************************************************************************************
 * Date           Author                   History  
 * 28-Apr-2016    ZhangYang               Initial
 * 
 **************************************************************************************************/


package com.uds.tc.dlg.createitem;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.uds.tc.dlg.createitem.Constants.ConstantsAutoLayout;

public class AutoLayout {

	protected Shell shell;
	private int shellWidth;
	private int shellHeight;
	private Composite composite;
	private Display display;
	private ScrolledComposite scroll;

	public static enum DialogResult {
		OK, CANCEL, OKFailure
	};

	private DialogResult dialogResult=DialogResult.CANCEL;

	// ��ȡ��Ļ���ĵ�
	public static Point getScreenCenterStartPoint(double width, double height) {
		int x = (int) ((ConstantsAutoLayout.WIDTH_SCREEN - width) / 2.0);
		int y = (int) ((ConstantsAutoLayout.HEIGHT_SCREEN - height) / 2.0);
		return new Point(x, y);
	}

	public AutoLayout(String title) {
		display = Display.getDefault();
		shell = new Shell(display, SWT.RESIZE | SWT.CLOSE | SWT.TOP);
		scroll = new ScrolledComposite(shell, SWT.BORDER | SWT.V_SCROLL|SWT.H_SCROLL);
		composite = new Composite(scroll, SWT.NONE);
		initialShell(display, title);
	}

	public Composite getComposite() {
		return composite;
	}

	public void CloseDialog(DialogResult result){
		dialogResult = result;
		shell.dispose();
	}
	/**
	 * Open the window.
	 */
	public void open(final List<List<DataControl>> datas,final int cols) {
		createContents(datas,cols);
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {

				display.sleep();
				
			}
		}
	}
		
	/**
	 * ��ʼ��Ĭ��ֵ
	 */
	private void initDefaultData() {
		shellWidth = ConstantsAutoLayout.DEFAULT_SHELL_WIDTH;
		shellHeight = ConstantsAutoLayout.DEFAULT_SHELL_HEIGHT;
	}

	private void initialShell(Display display, String title) {
		// ��ʼ��Ĭ��ֵ
		initDefaultData();
		// ���ô��ڴ�С
		// ���ô��ڱ���
		if (title != null)
			shell.setText(title);
		// ���ô��ڵĳ�ʼ��λ��
		shell.setLocation(getScreenCenterStartPoint(shellWidth, shellHeight));
		// ���ñ���ɫ
		shell.setForeground(display.getSystemColor(SWT.COLOR_WHITE));
		// ����ͼ��
		// ʹ��ClassLoader.getResourceAsStream������ͼ��
		this.getClass().getClassLoader().getResourceAsStream("../icons/uds.ico");
		// main��������ʱ�����·���������ͼ��
		// shell.setImage(new Image(display,
		// ConstantsAutoLayout.ICON_COMPANY_TRADEMARK));
		shell.setLayout(new FillLayout());
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents(List<List<DataControl>> datas,int cols) {
		int innerOuterHSpan = getInnerOuterHSpan(shell);
		int innerOuterVSpan = getInnerOuterVSpan(shell);
		
		scroll.setContent(composite);
		scroll.setExpandHorizontal(true);
		scroll.setExpandVertical(true);
		
		// ���ò���
		GridLayout gridLayout = new GridLayout(cols, false);

		int rowsHeight=autoLayout(gridLayout,datas);
		composite.setLayout(gridLayout);
		shell.setSize(shellWidth, rowsHeight + innerOuterVSpan);
		scroll.setMinSize(shell.getSize().x - scroll.getVerticalBar().getSize().x - innerOuterHSpan,
				shell.getSize().y - innerOuterVSpan - innerOuterHSpan / 2);
	}

	/**
	 * @return �߿�ײ�������߿򶥲���������ռ�ľ���
	 */
	public int getInnerOuterVSpan(Shell shell){
		if(shell!=null)
			return shell.getBounds().height - shell.getClientArea().height;
		return 0;
	}

	/**
	 * 
	 */
	public int getInnerOuterHSpan(Shell shell){
		if(shell!=null)
			return shell.getBounds().width - shell.getClientArea().width;
		return 0;
	}

	/**
	 * �Զ����ֲ����ز������֮��ĸ߶�
	 * 
	 * @param gridLayout
	 *            ���ַ�ʽΪ���񲼾�
	 * @param datas
	 *            ������Ϣ
	 * @return �߶�
	 */
	private int autoLayout(GridLayout gridLayout, List<List<DataControl>> datas) {
		gridLayout.marginTop = ConstantsAutoLayout.MARGIN_GRIDLAYOUT_TOP;
		gridLayout.marginLeft = ConstantsAutoLayout.MARGIN_GRIDLAYOUT_LEFT;
		gridLayout.marginRight = ConstantsAutoLayout.MARGIN_GRIDLAYOUT_RIGHT;
		gridLayout.marginBottom = ConstantsAutoLayout.MARGIN_GRIDLAYOUT_BOTTOM;
		//gridLayout.horizontalSpacing=0;
		//gridLayout.verticalSpacing=0;
		int rowsHeigth = 0;
		for (int row = 0; row < datas.size(); row++) {
			List<DataControl> controls = datas.get(row);
			int maxHeight = 0;
			for (int col = 0; col < controls.size(); col++) {
				DataControl dataControl = controls.get(col);
				Control control = dataControl.getControl();
				Point size = control.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				if (size.y > maxHeight)
					maxHeight = size.y;
				GridData gridData = new GridData(dataControl.gethAlignment(), dataControl.getvAlignment(),
						dataControl.grabExcessHorizontalSpace, dataControl.grabExcessVerticalSpace,
						dataControl.getColsSpan(), dataControl.getRowsSpan());
				gridData.verticalAlignment = SWT.CENTER;
				gridData.widthHint=dataControl.getWidth();
				gridData.heightHint=dataControl.getHeight();
				if (dataControl instanceof DataText) {
					if (gridData.heightHint > maxHeight)
						maxHeight = gridData.heightHint;
				}
				if (dataControl instanceof DataButton) {
					if (gridData.heightHint > maxHeight)
						maxHeight = gridData.heightHint;
				}
				control.setLayoutData(gridData);
			}
			rowsHeigth += maxHeight;
		}
		return rowsHeigth+gridLayout.marginTop + gridLayout.marginBottom
				+ gridLayout.verticalSpacing * (datas.size() * 3 / 2) + getInnerOuterVSpan(shell);
	}
	public void setShellWidth(int shellWidth) {
		this.shellWidth=shellWidth;
	}
	public DialogResult getDialogResult() {
		return dialogResult;
	}
}
