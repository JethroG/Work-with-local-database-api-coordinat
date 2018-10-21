package com.energycompany.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.energycompany.R;
import com.energycompany.workrwithCSV.Employee;

import java.util.List;

import static com.energycompany.R.layout.result_search_item;


public class SearchResultAdapter extends ArrayAdapter<Employee> {
    private final Context context;
    private final List<Employee> employees;

    public SearchResultAdapter(Context context, List<Employee> employees) {
        super(context, result_search_item, employees);
        this.context = context;
        this.employees = employees;
    }
    // Класс для сохранения во внешний класс и для ограничения доступа
    // из потомков класса
    private static class ViewHolder {
        TextView facescore;
         TextView fio;
         TextView adrees;
    }

    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        // ViewHolder буферизирует оценку различных полей шаблона элемента
        ViewHolder holder;
        // Очищает сущетсвующий шаблон, если параметр задан
        // Работает только если базовый шаблон для всех классов один и тот же
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            rowView = inflater.inflate(result_search_item, null);
            holder = new ViewHolder();
            holder.facescore = (TextView) rowView.findViewById(R.id.facescore1);
            holder.fio = (TextView) rowView.findViewById(R.id.fio1);
            holder.adrees = (TextView) rowView.findViewById(R.id.adrees1);
            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }
        Employee employee = employees.get(position);
        holder.facescore.setText("Л/С: "+employee.getPersonalAccount());
        holder.fio.setText("ФИО: "+employee.getNameFirstNameSecondName());
        holder.adrees.setText("Адрес: "+employee.getStreet()+" "+employee.getNumberhouse()+"  кв." +employee.getNumberpartment());
        return rowView;
    }
}
