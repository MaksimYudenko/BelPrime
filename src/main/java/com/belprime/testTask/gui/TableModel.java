package com.belprime.testTask.gui;

import javax.swing.table.AbstractTableModel;
import java.util.Map;

import static com.belprime.testTask.util.Constants.COLUMN_NAMES;

public class TableModel extends AbstractTableModel {
    private final Map<String, String> map;
    private final String[] columnNames = COLUMN_NAMES;
    private Object[][] data;

    TableModel(Map<String, String> map) {
        this.map = map;
        data = new Object[10][3];
        int j = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            data[j][0] = j + 1;
            data[j][1] = entry.getKey();
            data[j++][2] = entry.getValue();
        }
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return map.size();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex >= 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        data[rowIndex][columnIndex] = value;
        fireTableCellUpdated(rowIndex, columnIndex);
    }
}
