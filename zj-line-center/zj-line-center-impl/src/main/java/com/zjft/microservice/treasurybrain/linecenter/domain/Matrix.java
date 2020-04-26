package com.zjft.microservice.treasurybrain.linecenter.domain;

import java.io.Serializable;

public class Matrix implements Serializable{
	private int rowNum = -1; 
	private int colNum = -1; 
	private double[][] value;

	public double[][] getValue() {
		return this.value;
	}

	public Matrix(int rowNum, int colNum) {
		this.rowNum = rowNum;
		this.colNum = colNum;
		this.value = new double[this.rowNum][this.colNum];
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("rowNum=" + this.rowNum + ",");
		sb.append("colNum=" + this.colNum);
		return sb.toString();
	}

	public String toComplexString() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.toString() + ":");
		for (int i = 0; i < this.rowNum; i++) {
			sb.append(i + "[");
			for (int j = 0; j < this.colNum; j++) {
				sb.append(this.value[i][j] + ",");
			}
			sb.append(i + "]");
			if (i < this.rowNum - 1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	public int getRowNum() {
		return rowNum;
	}

	public int getColNum() {
		return colNum;
	}

	public double getValueItem(int rowIndex, int colIndex) {
		return this.value[rowIndex][colIndex];
	}

	public boolean setValueItem(int rowIndex, int colIndex, double valueItem) {
		try {
			this.value[rowIndex][colIndex] = valueItem;
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean addRow(double[] row) {
		return this.addRow(this.rowNum, row);
	}

	public boolean addRow(int rowIndex, double[] row) {
		try {
			if (rowIndex < 0 || rowIndex > this.rowNum) {
				return false;
			}
			int rowLen = row.length;
			if (rowLen != this.colNum) {
				return false;
			}

			double[][] temp = new double[this.rowNum + 1][this.colNum];

			for (int i = 0; i < rowIndex; i++) {
				System.arraycopy(this.value[i], 0, temp[i], 0, this.colNum);
			}
			temp[rowIndex] = row;
			for (int i = rowIndex + 1; i < this.rowNum + 1; i++) {
				System.arraycopy(this.value[i - 1], 0, temp[i], 0, this.colNum);
			}

			this.value = temp;
			this.rowNum++;

			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
