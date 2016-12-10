import sys
import json

file = sys.argv[1]


def main():
    # read traces
    traces = list(read_file())

    # separate threads
    thread_traces = separate_threads(traces)

    # make tree for every thread
    root = TraceNode('<root>')
    for thread_id, traces in thread_traces:
        t = make_tree(str(thread_id), traces)
        root.add(t)

    # output as xmind
    print(xmind(root))


def xmind(node):
    return '\n'.join(xmind_lines(node))


def xmind_lines(node):
    lines = []
    lines.append(node.text)
    for e in node.children:
        for line in xmind_lines(e):
            lines.append('\t' + line)
    return lines


def make_tree(name, traces):
    root = TraceNode(name)
    if not traces: return root
    target_depth = traces[0].depth
    next_level = []
    for e in traces:
        if e.direction == 'in': continue
        if e.depth != target_depth:
            next_level.append(e)
            continue
        if e.depth == target_depth:
            name = simple_method_name(e.clazz, e.method)
            node = make_tree(name, next_level)
            root.add(node)
            next_level = []
            continue
    return root


def simple_method_name(full_class, method_name):
    dot = full_class.rindex('.')
    simple_class = full_class[dot + 1:]
    if '$' not in simple_class: return simple_class + '.' + method_name
    dollar = simple_class.rindex('$')
    nest_class = simple_class[dollar + 1:]
    return nest_class + '.' + method_name


def separate_threads(traces):
    # all threads
    threads = []
    for e in traces:
        if e.thread not in threads:
            threads.append(e.thread)

    # every thread:
    result = []
    for e in threads:
        thread_traces = []
        for e2 in traces:
            if e2.thread == e:
                thread_traces.append(e2)
        result.append((e, thread_traces))
    return result


def read_file():
    with open(file) as f:
        for e in f:
            e = e.strip()
            if not e: continue
            yield parse_trace(e)


def parse_trace(trace):
    trace = json.loads(trace)
    result = Trace()
    result.time = trace['@timestamp']
    result.method = trace['method']
    result.clazz = trace['class']
    result.depth = trace['depth']
    result.thread = trace['thread']
    result.direction = trace['direction']
    return result


class Trace:
    direction = None
    thread = None
    depth = None
    clazz = None
    method = None
    time = None


class TraceNode:
    def __init__(self, text):
        self.text = text
        self.children = []

    def add(self, node):
        self.children.append(node)


main()
